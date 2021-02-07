package br.com.acalapi;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class App {

	@Autowired
	private AppConfig appConfig;

	public static void main(String[] args) {
		//createMongoDbUser();
		SpringApplication.run(App.class, args);
	}

	private static void createMongoDbUser() {
		MongoClient mongo = new MongoClient("acalv2.com.br", 27017);
		MongoDatabase db =  mongo.getDatabase("acal");
		Map<String, Object> commandArguments = new BasicDBObject();
		commandArguments.put("createUser", "alexandre");
		commandArguments.put("pwd", "senha");
		String[] roles = { "readWrite" };
		commandArguments.put("roles", roles);
		BasicDBObject command = new BasicDBObject(commandArguments);
		db.runCommand(command);
	}

}
