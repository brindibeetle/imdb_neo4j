package nl.lunatech.emile.imdb.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.lunatech.emile.imdb.domain.Person;
import nl.lunatech.emile.imdb.services.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

	// @Autowired
	private final PersonService personService;

	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping("/findByNconst")
	public Person findByNconst(@RequestParam(value = "nconst", defaultValue = "nn12345678") String nconst) {
		return personService.findByNconst(nconst);
	}
	@GetMapping("/findByPrimaryName")
	public Person findByPrimaryName(@RequestParam(value = "name", defaultValue = "Hauer") String primaryName) {
		return personService.findByPrimaryName(primaryName);
	}

	@GetMapping("/findByNameContaining")
	public Collection<Person> findByNameContaining(
			@RequestParam(value = "name", defaultValue = "Hauer") String primaryName) {
		return personService.findByNameContaining(primaryName);
	}

	@GetMapping("/findTheDegreeOfSeparation")
	public int findTheDegreeOfSeparation(
			@RequestParam(value = "nconst1", defaultValue = "nm1") String nconst1
			,@RequestParam(value = "nconst2", defaultValue = "nm2") String nconst2) {
		return personService.findTheDegreeOfSeparation(nconst1, nconst2);
	}

}
