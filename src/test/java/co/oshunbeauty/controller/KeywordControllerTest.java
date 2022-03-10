package co.oshunbeauty.controller;

import co.oshunbeauty.entity.Keyword;
import co.oshunbeauty.exception.BadRequestException;
import co.oshunbeauty.exception.ResourceNotFoundException;
import co.oshunbeauty.service.KeywordService;
import co.oshunbeauty.validation.ValidationsService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import static co.oshunbeauty.resources.EntitiesMocks.getKeyword;
import static co.oshunbeauty.resources.EntitiesMocks.getKeywords;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KeywordControllerTest {
	
	@InjectMocks
	KeywordController keywordController;
	
	@Mock
	KeywordService keywordService;
	
	@Mock
	ValidationsService validationsService;
	
	private static final Long BRAND_ID = 1L;
	private static final String KEY = "color";
	private static final String VALUE = "naranja";
	private static final String USER_TEST = "oshunTest";
	
	@Test
	public void testThatGetKeywordById() {
		// GIVEN
		Keyword keywordToFind = getKeyword();
		keywordToFind.setKeywordId(BRAND_ID);
		// WHEN
		when(keywordService.getKeywordById(any(Long.class))).thenReturn(Optional.of(keywordToFind));
		Keyword actualKeyword = keywordController.getKeywordById(BRAND_ID);
		// THEN
		verify(keywordService, times(1)).getKeywordById(any(Long.class));
		assertAll(
				() -> assertNotNull(actualKeyword),
				() -> assertEquals(BRAND_ID, actualKeyword.getKeywordId()),
				() -> assertEquals(KEY, actualKeyword.getKey()),
				() -> assertEquals(VALUE, actualKeyword.getValue())
		);
	}
	
	@Test
	public void testThatNotFoundGetKeywordById() {
		// GIVEN
		
		// WHEN
		when(keywordService.getKeywordById(any(Long.class))).thenReturn(Optional.empty());
		// THEN
		assertThrows(ResourceNotFoundException.class, () -> keywordController.getKeywordById(BRAND_ID));
		verify(keywordService, times(1)).getKeywordById(any(Long.class));
	}
	
	@Test
	public void testThatGetAllKeywords() {
		// GIVEN
		List<Keyword> keywordsToFind = getKeywords();
		// WHEN
		when(keywordService.getAllKeywords()).thenReturn(keywordsToFind);
		List<Keyword> actualKeywords = keywordController.getAllKeywords();
		// THEN
		verify(keywordService, times(1)).getAllKeywords();
		assertAll(
				() -> assertNotNull(actualKeywords),
				() -> assertEquals(4, actualKeywords.size()),
				() -> assertEquals(KEY, actualKeywords.get(0).getKey()),
				() -> assertEquals(VALUE, actualKeywords.get(0).getValue())
		);
	}
	
	@Test
	public void testThatGetKeywordByName() {
		// GIVEN
		List<Keyword> keywordsToFind = getKeywords().stream().filter(i -> i.getKey().contains(KEY))
				.collect(Collectors.toList());
		// WHEN
		when(keywordService.getKeywordsByKey(any(String.class))).thenReturn(keywordsToFind);
		List<Keyword> actualKeywords = keywordController.getKeywordsByName(KEY);
		// THEN
		verify(keywordService, times(1)).getKeywordsByKey(any(String.class));
		assertAll(
				() -> assertNotNull(actualKeywords),
				() -> assertEquals(2, actualKeywords.size()),
				() -> assertEquals(KEY, actualKeywords.get(0).getKey()),
				() -> assertEquals(VALUE, actualKeywords.get(0).getValue())
		);
	}
	
	@Test
	public void testThatSaveKeyword() {
		// GIVEN
		Keyword keywordToSave = getKeyword();
		Keyword keywordSaved = BeanUtils.instantiateClass(Keyword.class);
		BeanUtils.copyProperties(keywordToSave, keywordSaved);
		keywordSaved.setKeywordId(BRAND_ID);
		// WHEN
		when(keywordService.saveKeyword(any(Keyword.class), any(String.class)))
				.thenReturn(keywordSaved);
		Keyword actualKeyword = keywordController.saveKeyword(keywordToSave);
		// THEN
		verify(validationsService, times(1)).isKeywordValidToSave(any(Keyword.class));
		verify(keywordService, times(1)).saveKeyword(any(Keyword.class), any(String.class));
		assertAll(
				() -> assertNotNull(actualKeyword),
				() -> assertNotNull(actualKeyword.getCreationDate()),
				() -> assertNotNull(actualKeyword.getLastModifiedDate()),
				() -> assertEquals(BRAND_ID, actualKeyword.getKeywordId()),
				() -> assertEquals(KEY, actualKeyword.getKey()),
				() -> assertEquals(VALUE, actualKeyword.getValue()),
				() -> assertEquals("test", actualKeyword.getCreationUser())
		);
	}
	
	@Test
	public void testThatFailsSavingKeyword() {
		// GIVEN
		Keyword keywordToSave = getKeyword();
		// WHEN
		doThrow(BadRequestException.class).when(validationsService).isKeywordValidToSave(any(Keyword.class));
		// THEN
		assertThrows(BadRequestException.class,
				() -> keywordController.saveKeyword(keywordToSave));
		verify(validationsService,
				times(1)).isKeywordValidToSave(any(Keyword.class));
		verifyNoInteractions(keywordService);
	}
	
	@Test
	public void testThatFailsUpdatingKeywordDueToValidations() {
		// GIVEN
		Keyword keywordToSave = getKeyword();
		// WHEN
		doThrow(BadRequestException.class).when(validationsService).isKeywordValidToUpdate(any(Keyword.class));
		// THEN
		assertThrows(BadRequestException.class,
				() -> keywordController.updateKeyword(BRAND_ID, keywordToSave));
		verify(validationsService,
				times(1)).isKeywordValidToUpdate(any(Keyword.class));
		verifyNoInteractions(keywordService);
	}
	
	@Test
	public void testThatFailsUpdatingKeywordDueToWrongIds() {
		// GIVEN
		Keyword keywordToUpdate = getKeyword();
		keywordToUpdate.setKeywordId(3L);
		
		Keyword keywordFound = getKeyword();
		keywordFound.setKeywordId(BRAND_ID);
		// WHEN
		when(keywordService.getKeywordById(any(Long.class))).thenReturn(Optional.of(keywordFound));
		// THEN
		assertThrows(BadRequestException.class, () -> keywordController.updateKeyword(BRAND_ID, keywordToUpdate));
		verify(validationsService, times(1)).isKeywordValidToUpdate(any(Keyword.class));
		verify(keywordService, times(1)).getKeywordById(any(Long.class));
		verifyNoMoreInteractions(keywordService);
	}
	
	@Test
	public void testThatUpdateKeyword() {
		// GIVEN
		Keyword keywordToUpdate = getKeyword();
		keywordToUpdate.setKeywordId(BRAND_ID);
		
		Keyword keywordFound = getKeyword();
		keywordFound.setKeywordId(BRAND_ID);
		
		Keyword keywordUpdated = BeanUtils.instantiateClass(Keyword.class);
		BeanUtils.copyProperties(keywordToUpdate, keywordUpdated);
		keywordUpdated.setLastModifiedUser(USER_TEST);
		// WHEN
		when(keywordService.getKeywordById(any(Long.class))).thenReturn(Optional.of(keywordFound));
		when(keywordService.updateKeyword(any(Keyword.class), any(Keyword.class), any(String.class)))
				.thenReturn(keywordUpdated);
		Keyword actualKeyword = keywordController.updateKeyword(BRAND_ID, keywordToUpdate);
		// THEN
		verify(validationsService, times(1)).isKeywordValidToUpdate(any(Keyword.class));
		verify(keywordService, times(1)).getKeywordById(any(Long.class));
		verify(keywordService, times(1))
				.updateKeyword(any(Keyword.class), any(Keyword.class), any(String.class));
		assertAll(
				() -> assertNotNull(actualKeyword),
				() -> assertNotNull(actualKeyword.getCreationDate()),
				() -> assertNotNull(actualKeyword.getLastModifiedDate()),
				() -> assertEquals(BRAND_ID, actualKeyword.getKeywordId()),
				() -> assertEquals(KEY, actualKeyword.getKey()),
				() -> assertEquals(VALUE, actualKeyword.getValue()),
				() -> assertEquals(USER_TEST, actualKeyword.getLastModifiedUser())
		);
	}
	
	@Test
	public void testThatDeleteKeyword() {
		// GIVEN
		Keyword keywordFound = getKeyword();
		keywordFound.setKeywordId(BRAND_ID);
		
		// WHEN
		when(keywordService.getKeywordById(any(Long.class))).thenReturn(Optional.of(keywordFound));
		keywordController.deleteKeyword(BRAND_ID);
		// THEN
		verify(keywordService, times(1)).getKeywordById(any(Long.class));
		verify(keywordService, times(1)).deleteKeyword(any(Keyword.class));
	}
	
	@Test
	public void testThatFailsDeletingKeyword() {
		// GIVEN
		
		// WHEN
		when(keywordService.getKeywordById(any(Long.class))).thenReturn(Optional.empty());
		// THEN
		assertThrows(BadRequestException.class, () -> keywordController.deleteKeyword(BRAND_ID));
		verify(keywordService, times(1)).getKeywordById(any(Long.class));
		verifyNoMoreInteractions(keywordService);
	}
}
