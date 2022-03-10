package co.oshunbeauty.service;

import co.oshunbeauty.entity.Keyword;
import co.oshunbeauty.repository.KeywordRepository;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static co.oshunbeauty.constants.Constants.DateConstants.ZONE_ID;
import static co.oshunbeauty.constants.Constants.ServicesConstants.IGNORED_STANDARD_FIELDS;

@Service
public class KeywordService {
	
	private Set<String> ignoredKeywordFields = new HashSet<>(Arrays.asList("keywordId", "products"));
	private KeywordRepository keywordRepository;
	
	@Autowired
	public KeywordService(KeywordRepository keywordRepository) {
		this.keywordRepository = keywordRepository;
		ignoredKeywordFields.addAll(IGNORED_STANDARD_FIELDS);
	}
	
	public List<Keyword> getAllKeywords() {
		return keywordRepository.findAll();
	}
	
	public Optional<Keyword> getKeywordById(Long id) {
		return keywordRepository.findById(id);
	}
	
	public List<Keyword> getKeywordsByKey(String name) {
		return keywordRepository.findKeywordsByKey(name);
	}
	
	public Keyword saveKeyword(Keyword keyword, String user) {
		keyword.setCreationDate(ZonedDateTime.now(ZONE_ID));
		keyword.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
		keyword.setCreationUser(user);
		keyword.setLastModifiedUser(user);
		
		return keywordRepository.save(keyword);
	}
	
	public Keyword updateKeyword(Keyword currentKeyword, Keyword keywordSent, String user) {
		BeanUtils.copyProperties(keywordSent, currentKeyword, ignoredKeywordFields.stream().toArray(String[]::new));
		
		currentKeyword.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
		currentKeyword.setLastModifiedUser(user);
		
		return keywordRepository.save(currentKeyword);
	}
	
	public void deleteKeyword(Keyword keyword) {
		keywordRepository.delete(keyword);
	}
}
