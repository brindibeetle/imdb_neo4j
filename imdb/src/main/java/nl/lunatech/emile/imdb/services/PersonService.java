package nl.lunatech.emile.imdb.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.lunatech.emile.imdb.domain.Person;
import nl.lunatech.emile.imdb.repositories.PersonRepository;

@Service
public class PersonService {

    private final static Logger LOG = LoggerFactory.getLogger(PersonService.class);

	private final PersonRepository personRepository;
	
	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put(key1, value1);
		result.put(key2, value2);
		return result;
	}

    @Transactional(readOnly = true)
    public Person findByPrimaryName(String primaryName) {
        Person result = personRepository.findByPrimaryName(primaryName);
        return result;
    }

    @Transactional(readOnly = true)
    public Person findByNconst(String nconst) {
        Person result = personRepository.findByNconst(nconst);
        LOG.debug(result.toString());
        return result;
    }

    @Transactional(readOnly = true)
    public Collection<Person> findByNameContaining(String primaryName) {
        Collection<Person> result = personRepository.findByNameContaining(primaryName);
        return result;
    }

    @Transactional(readOnly = true)
	public int findTheDegreeOfSeparation(String nconst1, String nconst2) {
    	Integer result = personRepository.findTheDegreeOfSeparation(nconst1, nconst2);
    	if (result != null) return Integer.divideUnsigned(result, 2);
    	else return -1;
	}
   
}
