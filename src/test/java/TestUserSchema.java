import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

public class TestUserSchema {

    private Schema schema;

    @Before
    public void setUp() throws Exception {
        InputStream schemaStream = TestTeacher.class.getResourceAsStream("schemas/User.json");
        JSONObject rawSchema = new JSONObject(new JSONTokener(schemaStream));
        schema = SchemaLoader.builder()
                .useDefaults(true)
                .schemaJson(rawSchema)
                .build()
                .load().build();
    }


    @Test
    public void testPhoneNumberValidationWithValueIntegerFailure() {

        String testRequest = "{\n" +
                "\t\n" +
                "\n" +
                "    \"request\":{                 \n" +
                "      \"firstName\": \"run1eee\",\n" +
                "      \"lastName\": \"Kumar\",\n" +
                "      \"password\": \"password\",\n" +
                "      \"phone\": 987654321,\n" +
                "      \"userName\":\"run1df7eee9999d\",\n" +
                "      \"channel\":\"channel_01\",\n" +
                "      \"phoneVerified\":true\n" +
                "    }\n" +
                "} ";

        JSONObject obj = new JSONObject(testRequest);
        try {
            schema.validate(obj);
        } catch (ValidationException e) {
            Assert.assertEquals("#/request/phone: expected type: String, found: Integer", e.getAllMessages().get(0));
//            e.getCausingExceptions().stream()
//                    .map(ValidationException::getAllMessages)
//                    .forEach(System.out::println);
        }
    }

    @Test
    public void testPhoneNumberValidationWithValueStringSuccess() {

        String testRequest = "{\n" +
                "\t\n" +
                "\n" +
                "    \"request\":{                 \n" +
                "      \"firstName\": \"run1eee\",\n" +
                "      \"lastName\": \"Kumar\",\n" +
                "      \"password\": \"password\",\n" +
                "      \"phone\": \"9878553210\",\n" +
                "      \"userName\":\"run1df7eee9999d\",\n" +
                "      \"channel\":\"channel_01\",\n" +
                "      \"phoneVerified\":true\n" +
                "    }\n" +
                "}";

        JSONObject obj = new JSONObject(testRequest);
        try {
            schema.validate(obj);
            Assert.assertEquals(true, true);
        } catch (ValidationException e) {
            Assert.assertEquals("#/request/phone: expected type: String, found: Integer", e.getAllMessages().get(0));
        }
    }

    @Test
    public void testPhoneNumberValidationWithExtraDigitsFailure() {

        String testRequest = "{\n" +
                "\t\n" +
                "\n" +
                "    \"request\":{                 \n" +
                "      \"firstName\": \"run1eee\",\n" +
                "      \"lastName\": \"Kumar\",\n" +
                "      \"password\": \"password\",\n" +
                "      \"phone\": \"8318085722s\",\n" +
                "      \"userName\":\"run1df7eee9999d\",\n" +
                "      \"channel\":\"channel_01\",\n" +
                "      \"phoneVerified\":true\n" +
                "    }\n" +
                "}";

        JSONObject obj = new JSONObject(testRequest);
        try {
            schema.validate(obj);
            Assert.assertEquals(true, true);
        } catch (ValidationException e) {
            Assert.assertEquals("#/request/phone: string [8318085722s] does not match pattern [789][0-9]{9}", e.getAllMessages().get(0));
        }
    }


    @Test
    public void testMandatoryParamUserNameMissingFailure() {

        String testRequest = "{\n" +
                "\t\n" +
                "\n" +
                "    \"request\":{                 \n" +
                "      \"lastName\": \"Kumar\",\n" +
                "      \"password\": \"password\",\n" +
                "      \"phone\": \"8318085722\",\n" +
                "      \"channel\":\"channel_01\",\n" +
                "      \"phoneVerified\":true\n" +
                "    }\n" +
                "}";

        JSONObject obj = new JSONObject(testRequest);
        try {
            schema.validate(obj);
            Assert.assertEquals(true, true);
        } catch (ValidationException e) {
            Assert.assertEquals("#/request: required key [userName] not found", e.getAllMessages().get(0));
        }
    }

    @Test
    public void testMandatoryParamFirstNameMissingFailure() {

        String testRequest = "{\n" +
                "\t\n" +
                "\n" +
                "    \"request\":{                 \n" +
                "      \"lastName\": \"Kumar\",\n" +
                "      \"password\": \"password\",\n" +
                "      \"phone\": \"9878553210\",\n" +
                "      \"userName\":\"run1df7eee9999d\",\n" +
                "      \"channel\":\"channel_01\",\n" +
                "      \"phoneVerified\":true\n" +
                "    }\n" +
                "}";

        JSONObject obj = new JSONObject(testRequest);
        try {
            schema.validate(obj);
            Assert.assertEquals(true, true);
        } catch (ValidationException e) {
            Assert.assertEquals("#/request: required key [firstName] not found", e.getAllMessages().get(0));
        }
    }

    @Test
    public void testMandatoryParamLastNameMissingFailure() {

        String testRequest = "{\n" +
                "\t\n" +
                "\n" +
                "    \"request\":{                 \n" +
                "      \"firstName\": \"Kumar\",\n" +
                "      \"password\": \"password\",\n" +
                "      \"phone\": \"9878553210\",\n" +
                "      \"userName\":\"run1df7eee9999d\",\n" +
                "      \"channel\":\"channel_01\",\n" +
                "      \"phoneVerified\":true\n" +
                "    }\n" +
                "}";

        JSONObject obj = new JSONObject(testRequest);
        try {
            schema.validate(obj);
            Assert.assertEquals(true, true);
        } catch (ValidationException e) {
            Assert.assertEquals("#/request: required key [lastName] not found", e.getAllMessages().get(0));
        }
    }

    @Test
    public void testExpectedParamPhoneVerifiedWhenPhoneIsPresentSuccess() {

        ObjectNode testReq = JsonNodeFactory.instance.objectNode();
//        testReq.put()

        String testRequest = "{\n" +
                "\t\n" +
                "\n" +
                "    \"request\":{                 \n" +
                "      \"firstName\": \"Kumar\",\n" +
                "      \"lastName\": \"Kumar\",\n" +
                "      \"password\": \"password\",\n" +
                "      \"phone\": \"9878553210\",\n" +
                "      \"userName\":\"run1df7eee9999d\",\n" +
                "      \"channel\":\"channel_01\",\n" +
                "    }\n" +
                "}";

        JSONObject obj = new JSONObject(testRequest);
        try {
            schema.validate(obj);
            Assert.assertEquals(true, true);
        } catch (ValidationException e) {
            Assert.assertEquals("#/request: property [phoneVerified] is required", e.getAllMessages().get(0));
        }
    }

    @Test
    public void testAddressWhenAddTypeIsMissingFailure() {
        try {
            String testReq = "{\n" +
                    "\t\n" +
                    "\n" +
                    "    \"request\":{                 \n" +
                    "      \"firstName\": \"run1eee\",\n" +
                    "      \"lastName\": \"Kumar\",\n" +
                    "      \"password\": \"password\",\n" +
                    "      \"phone\": \"9878553210\",\n" +
                    "      \"userName\":\"run1df7eee9999d\",\n" +
                    "      \"channel\":\"channel_01\",\n" +
                    "      \"address\":{\n" +
                    "      \t\"country\":\"India\",\n" +
                    "      \t\"state\":\"uttarPradesh\"\n" +
                    "      },\n" +
                    "      \"phoneVerified\":true\n" +
                    "    }\n" +
                    "} ";
            JSONObject obj = new JSONObject(testReq);
            schema.validate(obj);
        } catch (ValidationException e) {
            System.out.println(e.getAllMessages());
            Assert.assertEquals("#/request/address: required key [addType] not found", e.getAllMessages().get(0));
        }

    }

    @Test
    public void testAddressWhenCountryIsMissingFailure() {
        try {
            String testReq = "{\n" +
                    "\t\n" +
                    "\n" +
                    "    \"request\":{                 \n" +
                    "      \"firstName\": \"run1eee\",\n" +
                    "      \"lastName\": \"Kumar\",\n" +
                    "      \"password\": \"password\",\n" +
                    "      \"phone\": \"9878553210\",\n" +
                    "      \"userName\":\"run1df7eee9999d\",\n" +
                    "      \"channel\":\"channel_01\",\n" +
                    "      \"address\":{\n" +
                    "      \t\"addType\":\"home\",\n" +
                    "      \t\"state\":\"uttarPradesh\"\n" +
                    "      },\n" +
                    "      \"phoneVerified\":true\n" +
                    "    }\n" +
                    "}";
            JSONObject obj = new JSONObject(testReq);
            schema.validate(obj);
        } catch (ValidationException e) {
            Assert.assertEquals("#/request/address: required key [country] not found", e.getAllMessages().get(0));
        }
    }


    @Test
    public void testExternalIdWhenExternalIdIsMissingFaliure() {

        String testReq = "{\n" +
                "    \"request\": {\n" +
                "        \"firstName\": \"run1eee\",\n" +
                "        \"lastName\": \"Kumar\",\n" +
                "        \"password\": \"password\",\n" +
                "        \"phone\": \"9878553210\",\n" +
                "        \"userName\": \"run1df7eee9999d\",\n" +
                "        \"channel\": \"channel_01\",\n" +
                "        \"phoneVerified\": true,\n" +
                "        \"externalId\": {\n" +
                "            \"idType\": \"channel\",\n" +
                "            \"provider\": \"channel\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        JSONObject object = new JSONObject(testReq);
        try {
            schema.validate(object);
        } catch (ValidationException e) {
            Assert.assertEquals("#/request/externalId: required key [externalId] not found",e.getAllMessages().get(0));
        }
    }
    @Test
    public void testExternalIdWhenExternalIdIsPresentSuccess() {

        String testReq = "{\n" +
                "    \"request\": {\n" +
                "        \"firstName\": \"run1eee\",\n" +
                "        \"lastName\": \"Kumar\",\n" +
                "        \"password\": \"password\",\n" +
                "        \"phone\": \"9878553210\",\n" +
                "        \"userName\": \"run1df7eee9999d\",\n" +
                "        \"channel\": \"channel_01\",\n" +
                "        \"phoneVerified\": true,\n" +
                "        \"externalId\": {\n" +
                "            \"idType\": \"channel\",\n" +
                "            \"provider\": \"channel\",\n" +
                "            \"externalId\":\"sjs\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        JSONObject object = new JSONObject(testReq);
        try {
            schema.validate(object);
            Assert.assertEquals(true,true);
        } catch (ValidationException e) {
        }
    }

    @Test
    public void testEducationWhenMandatoryParamsPresentSuccess(){
        String testReq="\n" +
                "\n" +
                "{\n" +
                "    \"request\": {\n" +
                "        \"firstName\": \"run1eee\",\n" +
                "        \"lastName\": \"Kumar\",\n" +
                "        \"password\": \"password\",\n" +
                "        \"phone\": \"9878553210\",\n" +
                "        \"userName\": \"run1df7eee9999d\",\n" +
                "        \"channel\": \"channel_01\",\n" +
                "        \"phoneVerified\": true,\n" +
                "        \"education\": [\n" +
                "        \t{\n" +
                "                \"degree\": \"btech\",\n" +
                "                \"name\": \"hello\",\n" +
                "                \"yearOfPassing\": 2019\n" +
                "            },\n" +
                "            {\n" +
                "                \"degree\": \"MBA\",\n" +
                "                \"name\": \"XYZ\",\n" +
                "                \"yearOfPassing\": 2014\n" +
                "            }\n" +
                "            \n" +
                "        ]\n" +
                "    }\n" +
                "}";
        JSONObject jsonObject=new JSONObject(testReq);
        try{
            schema.validate(jsonObject);
        }
        catch (ValidationException e){
            System.out.println(e.getAllMessages());
        }
    }
    @Test
    public void testEducationWhenMandatoryParamNameIsMissingFailure(){
        String testReq="\n" +
                "\n" +
                "{\n" +
                "    \"request\": {\n" +
                "        \"firstName\": \"run1eee\",\n" +
                "        \"lastName\": \"Kumar\",\n" +
                "        \"password\": \"password\",\n" +
                "        \"phone\": \"9878553210\",\n" +
                "        \"userName\": \"run1df7eee9999d\",\n" +
                "        \"channel\": \"channel_01\",\n" +
                "        \"phoneVerified\": true,\n" +
                "        \"education\": [\n" +
                "        \t{\n" +
                "                \"degree\": \"btech\",\n" +
                "                \"yearOfPassing\": 2019\n" +
                "            },\n" +
                "            {\n" +
                "                \"degree\": \"MBA\",\n" +
                "                \"name\": \"XYZ\",\n" +
                "                \"yearOfPassing\": 2014\n" +
                "            }\n" +
                "            \n" +
                "        ]\n" +
                "    }\n" +
                "}";
        JSONObject jsonObject=new JSONObject(testReq);
        try{
            schema.validate(jsonObject);
        }
        catch (ValidationException e){
            Assert.assertEquals("#/request/education/0: required key [name] not found",e.getAllMessages().get(0));
        }
    }
    @Test
    public void testEducationWhenMandatoryParamYearOfPassingIsMissingFailure(){
        String testReq="\n" +
                "\n" +
                "{\n" +
                "    \"request\": {\n" +
                "        \"firstName\": \"run1eee\",\n" +
                "        \"lastName\": \"Kumar\",\n" +
                "        \"password\": \"password\",\n" +
                "        \"phone\": \"9878553210\",\n" +
                "        \"userName\": \"run1df7eee9999d\",\n" +
                "        \"channel\": \"channel_01\",\n" +
                "        \"phoneVerified\": true,\n" +
                "        \"education\": [\n" +
                "        \t{\n" +
                "                \"degree\": \"btech\",\n" +
                "                \"name\": \"hello\",\n" +
                "                \"yearOfPassing\": 2019\n" +
                "            },\n" +
                "            {\n" +
                "                \"degree\": \"MBA\",\n" +
                "                \"name\": \"XYZ\",\n" +
                "            }\n" +
                "            \n" +
                "        ]\n" +
                "    }\n" +
                "}";
        JSONObject jsonObject=new JSONObject(testReq);
        try{
            schema.validate(jsonObject);
        }
        catch (ValidationException e){
            Assert.assertEquals("#/request/education/1: required key [yearOfPassing] not found",e.getAllMessages().get(0));
        }
    }
    @Test
    public void testLocationWhenLocationCodeAsListSuccess(){
        String testReq="{\n" +
                "    \"request\": {\n" +
                "        \"firstName\": \"run1eee\",\n" +
                "        \"lastName\": \"Kumar\",\n" +
                "        \"password\": \"password\",\n" +
                "        \"phone\": \"9878553210\",\n" +
                "        \"userName\": \"run1df7eee9999d\",\n" +
                "        \"channel\": \"channel_01\",\n" +
                "        \"phoneVerified\": true,\n" +
                "        \"locationCodes\": [\n" +
                "            \"91\",\n" +
                "            \"87\"\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        JSONObject jsonObject=new JSONObject(testReq);
        try{
            schema.validate(jsonObject);
            Assert.assertEquals(true,true);
        }
        catch (ValidationException e){
            System.out.println(e.getAllMessages());
        }
    }

    @Test
    public void testLocationWhenLocationCodeAsStringFailure(){
        String testReq="{\n" +
                "    \"request\": {\n" +
                "        \"firstName\": \"run1eee\",\n" +
                "        \"lastName\": \"Kumar\",\n" +
                "        \"password\": \"password\",\n" +
                "        \"phone\": \"9878553210\",\n" +
                "        \"userName\": \"run1df7eee9999d\",\n" +
                "        \"channel\": \"channel_01\",\n" +
                "        \"phoneVerified\": true,\n" +
                "        \"locationCodes\":\"98\"\n" +
                "    }\n" +
                "}";
        JSONObject jsonObject=new JSONObject(testReq);
        try{
            schema.validate(jsonObject);
        }
        catch (ValidationException e){
            Assert.assertEquals("#/request/locationCodes: expected type: JSONArray, found: String",e.getAllMessages().get(0));
        }
    }

    @Test
    public void testadD(){
        String testReq="\n" +
                "\n" +
                "{\n" +
                "    \"request\": {\n" +
                "        \"firstName\": \"run1eee\",\n" +
                "        \"lastName\": \"Kumar\",\n" +
                "        \"password\": \"password\",\n" +
                "        \"phone\": \"9878553210\",\n" +
                "        \"userName\": \"run1df7eee9999d\",\n" +
                "        \"channel\": \"channel_01\",\n" +
                "        \"phoneVerified\": true,\n" +
                "        \"education\": [\n" +
                "        \t{\n" +
                "                \"degree\": \"btech\",\n" +
                "                \"name\": \"hello\",\n" +
                "                \"yearOfPassing\": 2019\n" +
                "            },\n" +
                "            {\n" +
                "                \"degree\": \"btecdh\",\n" +
                "                \"name\": \"hellof\",\n" +
                "                \"yearOfPassing\": 20119\n" +
                "            }\n" +
                "            \n" +
                "        ]\n" +
                "    }\n" +
                "}";
    try {
        JSONObject s=new JSONObject(testReq);
        schema.validate(s);
        System.out.println("valudadddd");
    }
    catch (ValidationException e){
        System.out.println(e.getAllMessages());
    }
    }

}
