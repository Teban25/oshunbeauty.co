package co.oshunbeauty.service;

import co.oshunbeauty.entity.Keyword;
import co.oshunbeauty.repository.KeywordRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;

import static co.oshunbeauty.resources.EntitiesMocks.getKeyword;
import static co.oshunbeauty.resources.EntitiesMocks.getKeywords;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class KeywordServiceTest {
	
	@Mock
	private KeywordRepository keywordRepository = Mockito.mock(KeywordRepository.class);
	
	private KeywordService keywordService = new KeywordService(keywordRepository);
	
	private static final String USER_TEST = "testOshun";
	private static final String KEY = "color";
	private static final String VALUE = "naranja";
	
	@BeforeEach
	public void setUp() {
	}
	
	@Test
	public void testGetKeywordByIdNotFound(){
		// Given
		Long keywordId = 1L;
		// When
		Mockito.when(keywordRepository.findById(any(Long.class))).thenReturn(Optional.empty());
		Optional<Keyword> keywordFound = keywordService.getKeywordById(keywordId);
		// Then
		verify(keywordRepository, times(1)).findById(any(Long.class));
		assertTrue(keywordFound.isEmpty());
	}
	
	@Test
	public void testGetKeywordById() {
		// Given
		Long keywordId = 1L;
		Keyword keywordToFind = getKeyword();
		keywordToFind.setKeywordId(keywordId);
		// When
		Mockito.when(keywordRepository.findById(any(Long.class))).thenReturn(Optional.of(keywordToFind));
		Optional<Keyword> keywordFound = keywordService.getKeywordById(keywordId);
		// Then
		verify(keywordRepository, times(1)).findById(any(Long.class));
		assertAll(
				() -> assertTrue(keywordFound.isPresent()),
				() -> assertEquals(keywordId, keywordFound.get().getKeywordId()),
				() -> assertEquals(KEY, keywordFound.get().getKey()),
				() -> assertEquals(VALUE, keywordFound.get().getValue())
		);
	}
	
	@Test
	public void testToGetAllKeywords() {
		// Given
		List<Keyword> keywords = getKeywords();
		// When
		Mockito.when(keywordRepository.findAll()).thenReturn(keywords);
		List<Keyword> currentKeywords = keywordService.getAllKeywords();
		// Then
		verify(keywordRepository, times(1)).findAll();
		assertAll(
				() -> assertNotNull(currentKeywords),
				() -> assertEquals(4, currentKeywords.size()),
				() -> assertEquals(KEY, currentKeywords.get(0).getKey()),
				() -> assertEquals(VALUE, currentKeywords.get(0).getValue())
		);
	}
	
	@Test
	public void testToGetKeywordsByKey() {
		// Given
		List<Keyword> keywords = getKeywords().stream().filter(i -> i.getKey().contains(KEY))
				.collect(Collectors.toList());
		// When
		Mockito.when(keywordRepository.findKeywordsByKey(any(String.class))).thenReturn(keywords);
		List<Keyword> currentKeyword = keywordService.getKeywordsByKey(KEY);
		// Then
		verify(keywordRepository, times(1)).findKeywordsByKey(any(String.class));
		assertAll(
				() -> assertNotNull(currentKeyword),
				() -> assertEquals(2, currentKeyword.size()),
				() -> assertEquals(KEY, currentKeyword.get(0).getKey()),
				() -> assertEquals(VALUE, currentKeyword.get(0).getValue())
		);
	}
	
	@Test
	public void testToUpdateKeyword(){
		// Given
		Keyword keywordToUpdate = getKeyword();
		keywordToUpdate.setKeywordId(1L);
		keywordToUpdate.setKey("codigo");
		keywordToUpdate.setValue("1025412H");
		
		Keyword currentKeyword = getKeyword();
		currentKeyword.setKeywordId(1L);
		
		Keyword keywordUpdated = BeanUtils.instantiateClass(Keyword.class);
		BeanUtils.copyProperties(keywordToUpdate, keywordUpdated);
		keywordUpdated.setLastModifiedUser(USER_TEST);
		
		// When
		Mockito.when(keywordRepository.save(any(Keyword.class))).thenReturn(keywordUpdated);
		Keyword currentKeywordUpdated = keywordService.updateKeyword(currentKeyword, keywordToUpdate, USER_TEST);
		// Then
		verify(keywordRepository, times(1)).save(any(Keyword.class));
		assertAll(
				() -> assertNotNull(currentKeywordUpdated),
				() -> assertEquals(USER_TEST, currentKeywordUpdated.getLastModifiedUser()),
				() -> assertEquals("codigo", currentKeywordUpdated.getKey()),
				() -> assertEquals("1025412H", currentKeywordUpdated.getValue())
		);
	}
	
	@Test
	public void testToSaveKeyword(){
		// Given
		Keyword keywordToSave = getKeyword();
		// When
		Mockito.when(keywordRepository.save(any(Keyword.class))).thenReturn(keywordToSave);
		Keyword currentKeyword = keywordService.saveKeyword(keywordToSave, USER_TEST);
		// Then
		verify(keywordRepository, times(1)).save(any(Keyword.class));
		assertAll(
				() -> assertNotNull(currentKeyword),
				() -> assertEquals(USER_TEST, currentKeyword.getCreationUser()),
				() -> assertEquals(KEY, currentKeyword.getKey()),
				() -> assertEquals(VALUE, currentKeyword.getValue())
		);
	}
}
