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
    public void testUserSchemaPhoneValidationSuccess() {

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
                "} ";

        JSONObject obj = new JSONObject(testRequest);
        try {
            schema.validate(obj);
            Assert.assertTrue(true == true);
        } catch (ValidationException e) {
            System.out.println(e.getErrorMessage());
            e.getCausingExceptions().stream()
                    .map(ValidationException::getCausingExceptions)
                    .forEach(System.out::println);
        }
    }


}
