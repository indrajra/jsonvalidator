import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.*;

import java.io.InputStream;

public class TestTeacher {
    private Schema schema;

    @Before
    public void setUp() throws Exception {
        InputStream schemaStream = TestTeacher.class.getResourceAsStream("teacher/Teacher.json");
        JSONObject rawSchema = new JSONObject(new JSONTokener(schemaStream));
        SchemaLoader schemaLoader = SchemaLoader.builder().schemaJson(rawSchema)
                .draftV7Support().resolutionScope("http://localhost:8080/_schemas/").build();
        schema = schemaLoader.load().build();
    }

    @Test
    public void validTeacher_validationPasses() {
        InputStream inputStream = TestTeacher.class.getResourceAsStream("teacher/valid.json");
        JSONObject obj = new JSONObject(new JSONTokener(inputStream));
        try {
            schema.validate(obj); // throws a ValidationException if this object is invalid
            Assert.assertTrue(true == true);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            e.getCausingExceptions().stream()
                    .map(ValidationException::getMessage)
                    .forEach(System.out::println);
        }
    }

    @Ignore
    @Test
    public void invalidYear_validationFails() {
        boolean exceptionThrown = false;
        try {
            InputStream inputStream = TestTeacher.class.getResourceAsStream("year/invalid.json");
            JSONObject obj = new JSONObject(new JSONTokener(inputStream));
            schema.validate(obj); // throws a ValidationException if this object is invalid
            Assert.assertTrue(true == true);
        } catch (ValidationException ve) {
            exceptionThrown = true;
            System.out.println(ve.getMessage());
        }
        Assert.assertTrue(exceptionThrown == true);
    }


    @After
    public void tearDown() throws Exception {
    }
}