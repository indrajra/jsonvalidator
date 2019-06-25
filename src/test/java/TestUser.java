import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

public class TestUser {

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
    public void testUserSchemaPhoneValidationSuccess() {

        String testRequest = "{\n" +
                "   \"request\":{            \n" +
                "      \"firstName\": \"run1eee\",\n" +
                "      \"lastName\": \"Kumar\",\n" +
                "      \"password\": \"password\",\n" +
                "      \"phone\": \"9878553210\",\n" +
                "      \"userName\":\"run17eee9999d\",\n" +
                "      \"channel\":\"channel_01\",\n" +
                "      \"phoneVerified\":true,\n" +
                "      \"userType\":\"TEACHER\"\n" +
                "    \n" +
                "}}";

        JSONObject obj = new JSONObject(testRequest);
        try {
            schema.validate(obj);
            Assert.assertTrue(true == true);
        } catch (ValidationException e) {
            e.getCausingExceptions().stream()
                    .map(ValidationException::getMessage)
                    .forEach(System.out::println);
        }
    }


}
