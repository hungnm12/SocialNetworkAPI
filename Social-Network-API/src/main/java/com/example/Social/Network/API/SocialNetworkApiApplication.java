package com.example.Social.Network.API;

import com.sun.tools.javac.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class  SocialNetworkApiApplication {

	public static void main(String[] args) {
		File file = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String projectRoot = file.getParent();
		System.out.println("PROJECT_ROOT: " + projectRoot);
		SpringApplication.run(SocialNetworkApiApplication.class, args);
	}

}
