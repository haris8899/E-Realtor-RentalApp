//SPDX-License-Identifier:MIT

pragma solidity ^0.8.13;

contract ERealtor_Property{

    //State variables
    uint256   no_of_properties = 0;
    uint256   no_of_property_Agreements = 0;
    uint256   rent_paid_in_total = 0;
    address payable property_tenant;
    address payable property_landlord;

    //sturct to store property details
    struct property{
        uint256 property_id;
        string property_title;
        string property_address;
        uint256 rent_per_month;
        uint256 advance_amount;
        uint256 timestamp;
        bool property_availablity;
        uint256 property_agreement_id;
        address payable current_property_tenant;
        address payable current_property_landlord;
    }

    struct PropertyAgreementDetails{
        uint256 property_id;
        string property_title;
        string property_address;
        uint256 rent_per_month;
        uint256 advance_amount;
        uint256 timestamp;
        uint256 Agreement_duration;
        uint256 property_agreement_id;
        address payable property_Aggrement_tenant;
        address payable property_Aggrement_landlord;
    }

    struct Property_Rent_Details{
        uint256 property_rent_no;
        uint256 property_id;
        string property_title;
        string property_address;
        uint256 rent_per_month;
        uint256 timestamp;
        uint256 property_agreement_id;
        address payable property_rent_tenant;
        address payable property_rent_landlord;
    }



    //All mappings
    mapping(uint256 => property) public property_no;
    mapping(uint256 => PropertyAgreementDetails) public AgreementMapping;
    mapping(uint256 => Property_Rent_Details) public property_rent_no;

   
    //Modifiers
    
    //check wether property is available or not
    modifier checkAvailablity(uint256 _propertyNo){
        require(property_no[_propertyNo].property_availablity==true, "Property currently not available...");
        _;
    }

    modifier verifyLandlord(uint256 _propertyNo){
        require(property_no[_propertyNo].current_property_landlord== msg.sender,"Access denied only landord can access it");
        _;
    }

    modifier verifyTenant(uint256 _propertyNo){
        require(property_no[_propertyNo].current_property_landlord!= msg.sender,"Access denied only tenant can access it");
        _;
    }
    //check the account details wether it have sufficient money to pay rent
    modifier check_credit_details(uint256 _id){
        require(msg.value >= uint256(property_no[_id].rent_per_month),"Balance is Insufficient");
        _;
    }
    //check the account details wether it have sufficient money to pay advance
    modifier check_advance_details(uint256 _id){
        require(msg.value >= uint256(property_no[_id].rent_per_month)+ uint256(property_no[_id].advance_amount),"Balance is Insufficient");
        _;
    }
    //check same tenant address 
    modifier verify_Same_user(uint256 _id){
        require(property_no[_id].current_property_tenant== msg.sender, "No agreement found between the parties");
        _;
    }
    // check contract remaining time
    modifier Check_Contract_Timeleft(uint _id){
        uint256 _Agrementcount = property_no[_id].property_agreement_id;
        uint256 timeLeft = AgreementMapping[_Agrementcount].timestamp + AgreementMapping[_Agrementcount].Agreement_duration;
        require(block.timestamp < timeLeft, "Contract Already Terminated");
        _;
    }
    //Check for the Agrement Time's Up or not 
    modifier Contract_TimesUp(uint _index) {
        uint _AgreementNo = property_no[_index].property_agreement_id;
        uint time = AgreementMapping[_AgreementNo].timestamp + AgreementMapping[_AgreementNo].Agreement_duration;
        require(block.timestamp > time, "Time is left for contract to end");
        _;
    }

    // Check that 30 days have passed after the last rent Payment
    modifier Rent_Times_Finished(uint _index) {
        uint256 time = property_no[_index].timestamp + 30 days;
        require(block.timestamp >= time, "Time left to pay Rent");
        _;
    }

    //Functions

    //function to addproperty
    function addProperty(string memory _pTitle, string memory _pAddress, uint256 _rent, uint256  _advance) public {
        require(msg.sender != address(0));
        no_of_properties ++;
        property_no[no_of_properties] = property(no_of_properties,_pTitle,_pAddress, _rent,_advance,0,true,0, payable(address(0)),payable(msg.sender)); 
    }

    // used to sign agreement
    function signAgreement(uint256 _index) public payable verifyTenant(_index) check_advance_details(_index) checkAvailablity(_index) {
        require(msg.sender != address(0));
        address payable _landlord = property_no[_index].current_property_landlord;
        uint totalfee = property_no[_index].rent_per_month + property_no[_index].advance_amount;
        _landlord.transfer(totalfee);
        no_of_property_Agreements++;
        property_no[_index].current_property_tenant = payable(msg.sender);
        property_no[_index].property_availablity = false;
        property_no[_index].timestamp = block.timestamp;
        property_no[_index].property_agreement_id = no_of_property_Agreements;
        AgreementMapping[no_of_property_Agreements]=PropertyAgreementDetails(_index,property_no[_index].property_title,property_no[_index].property_address,property_no[_index].rent_per_month,property_no[_index].advance_amount,block.timestamp,365 days,no_of_property_Agreements,payable(msg.sender),payable(_landlord));
        rent_paid_in_total++;
        property_rent_no[rent_paid_in_total] = Property_Rent_Details(rent_paid_in_total,_index,property_no[_index].property_title,property_no[_index].property_address,property_no[_index].rent_per_month,block.timestamp,no_of_property_Agreements,payable(msg.sender),payable(_landlord));
    }

    //function will execute only when the duration is complete 
     function ContractCompleted(uint _index) public payable verifyLandlord(_index) Contract_TimesUp(_index){
        require(msg.sender != address(0));
        require(property_no[_index].property_availablity == false, "Property is currently not available.");
        property_no[_index].property_availablity = true;
        address payable _Tenant = property_no[_index].current_property_tenant;
        uint _advance = property_no[_index].advance_amount;
        _Tenant.transfer(_advance);
    }

    //Function will only execute if the tenant had paid his/her previous rent more than a month ago.
    function payRent(uint _index) public payable verifyTenant(_index) Rent_Times_Finished(_index) check_credit_details(_index){
        require(msg.sender != address(0));
        address payable _landlord = property_no[_index].current_property_landlord;
        uint _rent = property_no[_index].rent_per_month;
        _landlord.transfer(_rent);
        property_no[_index].current_property_tenant =payable(msg.sender);
        property_no[_index].property_availablity = false;
        rent_paid_in_total++;
        property_rent_no[rent_paid_in_total] = Property_Rent_Details(rent_paid_in_total,_index,property_no[_index].property_title,property_no[_index].property_address,_rent,block.timestamp,property_no[_index].property_agreement_id,payable(msg.sender),property_no[_index].current_property_landlord);
    }
    

     //Function to finish the Terminated Agreement
    function agreementTerminated(uint _index) public payable verifyLandlord(_index) Check_Contract_Timeleft(_index){
        require(msg.sender != address(0));
        property_no[_index].property_availablity = true;
        uint _securitydeposit = property_no[_index].advance_amount;
        address payable _Tenant = property_no[_index].current_property_tenant;
        _Tenant.transfer(_securitydeposit);
        
    }

    //view all agreements
    function getAllAgreements() public view returns(uint256[] memory property_ID ,uint256[] memory Rent,uint256[] memory Advance,uint256[] memory Duration,uint256[] memory Aid,address[] memory  Tenant,address[] memory  Landlord){ 
        uint256[] memory pid = new uint256[](no_of_property_Agreements);
        uint256[] memory rent = new uint256[](no_of_property_Agreements);
        uint256[] memory advance = new uint256[](no_of_property_Agreements);
        uint256[] memory dura = new uint256[](no_of_property_Agreements);
        uint256[] memory aid = new uint256[](no_of_property_Agreements);
        address[] memory ten= new address[](no_of_property_Agreements);
        address[] memory land= new address[](no_of_property_Agreements);
        
        uint256 index = 0;
        for (uint256 i = 0; i < 256; i++) {
            if (AgreementMapping[i].property_id != 0 && AgreementMapping[i].rent_per_month != 0 && AgreementMapping[i].advance_amount != 0 && AgreementMapping[i].timestamp != 0 && AgreementMapping[i].Agreement_duration != 0 && AgreementMapping[i].property_agreement_id != 0) {
                pid[index] = AgreementMapping[i].property_id;
                rent[index] = AgreementMapping[i].rent_per_month;
                advance[index] = AgreementMapping[i].advance_amount;
                dura[index] = AgreementMapping[i].Agreement_duration;
                aid[index] = AgreementMapping[i].property_agreement_id;
                ten[index] = AgreementMapping[i].property_Aggrement_tenant;
                land[index] = AgreementMapping[i].property_Aggrement_landlord;
                
                index++;
            }
        }

        return (pid,rent,advance,dura,aid,ten,land);
    }
    
}

