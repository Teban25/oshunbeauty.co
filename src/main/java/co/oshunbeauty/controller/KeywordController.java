package co.oshunbeauty.controller;

import co.oshunbeauty.entity.Keyword;
import co.oshunbeauty.exception.BadRequestException;
import co.oshunbeauty.exception.ResourceNotFoundException;
import co.oshunbeauty.service.KeywordService;
import co.oshunbeauty.validation.ValidationsService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rs/keywords")
@Slf4j
public class KeywordController {
	
	private ValidationsService validationsService;
	private KeywordService keywordService;
	
	@Autowired
	public KeywordController(ValidationsService validationsService, KeywordService keywordService) {
		this.validationsService = validationsService;
		this.keywordService = keywordService;
	}
	
	@GetMapping("/")
	public List<Keyword> getAllKeywords() {
		return keywordService.getAllKeywords();
	}
	
	@GetMapping("/{id}")
	public Keyword getKeywordById(@PathVariable final Long id) {
		Optional<Keyword> keywordFound = keywordService.getKeywordById(id);
		
		if(keywordFound.isEmpty()) {
			log.error("The keyword with id {} was not found.", id);
			throw new ResourceNotFoundException(getMessageForKeywordNotFoundException(id));
		}
		
		return keywordFound.get();
	}
	
	@GetMapping("/key")
	public List<Keyword> getKeywordsByName(@RequestParam final String key) {
		return keywordService.getKeywordsByKey(key);
	}
	
	@PostMapping
	public Keyword saveKeyword(@RequestBody final Keyword keyword) {
		validationsService.isKeywordValidToSave(keyword);
		
		log.info("Saving new keyword with name {} by the user {}", keyword.getKey(), "oshun");
		return keywordService.saveKeyword(keyword, "oshun");
	}
	
	@PutMapping("/{id}")
	public Keyword updateKeyword(@PathVariable final Long id, @RequestBody final Keyword keyword) {
		validationsService.isKeywordValidToUpdate(keyword);
		Optional<Keyword> currentKeywordFound = keywordService.getKeywordById(id);
		validateKeywordsAreEqualsById(keyword, currentKeywordFound);
		
		log.info("Updating the keyword with name {} by the user {}", keyword.getKey(), "oshun");
		return keywordService.updateKeyword(currentKeywordFound.get(), keyword, "oshun");
	}
	
	@DeleteMapping("/{id}")
	public void deleteKeyword(@PathVariable final Long id) {
		Optional<Keyword> currentKeywordFound = keywordService.getKeywordById(id);
		if(currentKeywordFound.isEmpty()) {
			log.error("The keyword with id {} was not found.", id);
			throw new BadRequestException(getMessageForKeywordNotFoundException(id));
		}
		
		log.info("Deleting the keyword with name {} by the user {}", currentKeywordFound.get().getKey(), "oshun");
		keywordService.deleteKeyword(currentKeywordFound.get());
	}
	
	private void validateKeywordsAreEqualsById(Keyword keyword, Optional<Keyword> currentKeywordFound) {
		if(currentKeywordFound.isEmpty() || currentKeywordFound.get().getKeywordId() != keyword.getKeywordId() ) {
			log.error("When trying to update keyword with id {}, the keyword sent had another id",
					keyword.getKeywordId());
			throw new BadRequestException(getErrorMessageKeywordsAreNotSame(keyword));
		}
	}
	
	private String getErrorMessageKeywordsAreNotSame(Keyword keyword) {
		return String.format("La palabra clave con id %s no fue encontrada o no corresponde a la " +
				"ingresada", keyword.getKeywordId());
	}
	
	private String getMessageForKeywordNotFoundException(Long id) {
		return String.format("La palabra clave con id %s no fue encontrada", id);
	}
}
