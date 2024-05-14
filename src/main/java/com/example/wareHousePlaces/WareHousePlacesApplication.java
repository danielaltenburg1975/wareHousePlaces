package com.example.wareHousePlaces;

import com.example.wareHousePlaces.messageManager.MessageManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Local;

import java.util.Locale;

/**
 * The Warehouse component determines the best storage location in pallet racks. The RESTful
 * application receives article number, height, quantity and pallet type in Json format.
 * The result is evaluated so that alternatives can also be considered.
 */

@SpringBootApplication
public class WareHousePlacesApplication {

	public static void main(String[] args) {


		SpringApplication.run(WareHousePlacesApplication.class, args);

		 Locale germanLocale = new Locale("de", "DE");
		 Locale englishLocale = new Locale("en", "US");

		MessageManager.loadMessages(germanLocale);
	}

}
