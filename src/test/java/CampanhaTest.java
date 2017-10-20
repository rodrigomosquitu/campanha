import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import com.campanha.repository.CampRepository;
import com.campanha.repository.CampanhaRepositoryImpl;
import com.campanha.repository.ClienteRepository;
import com.campanha.repository.ClienteRepositoryImpl;

import io.restassured.RestAssured;
import org.junit.runners.MethodSorters;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CampanhaTest {
	
	public static Map<String, Object>  jsonCliente = new HashMap<String, Object>();
	
	@BeforeClass
    public static void init() {
		@SuppressWarnings("resource")
		ConfigurableApplicationContext context = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/springrest-servlet.xml");
		
		CampRepository campanhaRep = context.getBean(CampanhaRepositoryImpl.class);
		campanhaRep.dropCollection();
		campanhaRep.createCollection();
		
		ClienteRepository clienteRep = context.getBean(ClienteRepositoryImpl.class);
		clienteRep.dropCollection();
		clienteRep.createCollection();
		
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        
		jsonCliente.put("id",            1);
		jsonCliente.put("nome",          "Rodrigo");
		jsonCliente.put("idTimeCoracao", 1);
		jsonCliente.put("dataNasc",      "1976-02-13T17:30:01.882Z");
		jsonCliente.put("email",     	 "rodrigomosquitu@gmail.com");
    }

    @Test
    public void t1() {
        
    	Map<String, Object>  jsonAsMap = new HashMap<String, Object>();
    	jsonAsMap.put("id",            1);
    	jsonAsMap.put("nome",          "Campanha 1");
    	jsonAsMap.put("idTimeCoracao", 1);
    	jsonAsMap.put("dataInicial",   "2017-10-01T17:30:01.882Z");
    	jsonAsMap.put("dataFinal",     "2017-11-18T17:30:01.882Z");

    	given().
    	        contentType("application/json; charset=UTF-16").
    	        body(jsonAsMap).
    	when().
    	        post("/campanha/add").
    	then().
    	        statusCode(200);
    }
    
    @Test
    public void t2() {
        
    	Map<String, Object>  jsonAsMap = new HashMap<String, Object>();
    	jsonAsMap.put("id",            2);
    	jsonAsMap.put("nome",          "Campanha 2");
    	jsonAsMap.put("idTimeCoracao", 2);
    	jsonAsMap.put("dataInicial",   "2017-10-01T17:30:01.882Z");
    	jsonAsMap.put("dataFinal",     "2017-11-17T17:30:01.882Z");

    	given().
    	        contentType("application/json; charset=UTF-16").
    	        body(jsonAsMap).
    	when().
    	        post("/campanha/add").
    	then().
    	        statusCode(200);
    }
    
    @Test
    public void t3() {
   
    	Map<String, Object>  jsonAsMap = new HashMap<String, Object>();
    	jsonAsMap.put("id",            3);
    	jsonAsMap.put("nome",          "Campanha 3");
    	jsonAsMap.put("idTimeCoracao", 3);
    	jsonAsMap.put("dataInicial",   "2017-10-01T17:30:01.882Z");
    	jsonAsMap.put("dataFinal",     "2017-11-18T17:30:01.882Z");

    	given().
    	        contentType("application/json; charset=UTF-16").
    	        body(jsonAsMap).
    	when().
    	        post("/campanha/add").
    	then().
    			body("vigencia", hasItems("2017-10-01T17:30Z - 2017-11-20T17:30Z", "2017-10-01T17:30Z - 2017-11-19T17:30Z", "2017-10-01T17:30Z - 2017-11-18T17:30Z"));
    }
    
    @Test
    public void t4() {

    	given().
    	        contentType("application/json; charset=UTF-16").
    	        body(jsonCliente).
    	when().
    	        post("/campanha/addcliente").
    	then().
        		statusCode(200);
    }
    
    @Test
    public void t5() {

    	given().
    	        contentType("application/json; charset=UTF-16").
    	        body(jsonCliente).
    	when().
    	        post("/campanha/addcliente").
    	then().
				body("nome", hasItems("Campanha 1"));
    }
    
    @Test
    public void t6() {

    	get("/campanha/associa/1").
    	then().
				statusCode(200);
    }
    
    @Test
    public void t7() {

    	given().
    	        contentType("application/json; charset=UTF-16").
    	        body(jsonCliente).
    	when().
    	        post("/campanha/addcliente").
    	then().
				body("message", equalTo("Usuário já cadastrado com esse email!"));
    }
    
    @Test
    public void t8() {

    	get("/campanha/logs").
    	then().
				body("dataFinalNew", hasItems("Sun Nov 19 15:30:01 BRST 2017", "Mon Nov 20 15:30:01 BRST 2017"));
    }
    
}
