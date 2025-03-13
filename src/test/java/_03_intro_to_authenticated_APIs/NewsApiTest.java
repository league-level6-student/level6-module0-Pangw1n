package _03_intro_to_authenticated_APIs;

import _03_intro_to_authenticated_APIs.data_transfer_objects.ApiExampleWrapper;
import _03_intro_to_authenticated_APIs.data_transfer_objects.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class NewsApiTest {

    NewsApi newsApi;
    
    @Mock
    WebClient webClient;

    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    
    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpec;
    
    @Mock
    ResponseSpec responseSpec;
    
    @Mock
    Mono<ApiExampleWrapper> monoResult;
    
    @Mock
    ApiExampleWrapper mockResult;
    
    @Mock
    List<Article> mockArticles;
    
    @Mock
    Article mockArticle;
    

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    	newsApi = new NewsApi();
    	newsApi.setWebClient(webClient);
    }

    @Test
    void itShouldGetNewsStoryByTopic() {
        //given
    	String topic = "test";
    	ApiExampleWrapper result = new ApiExampleWrapper();
    	when(webClient.get()).thenReturn(requestHeadersUriSpec);
    	when(requestHeadersUriSpec.uri( (Function<UriBuilder, URI>) any()) ).thenReturn(requestHeadersSpec);
    	when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    	when(responseSpec.bodyToMono(ApiExampleWrapper.class)).thenReturn(monoResult);
    	when(monoResult.block()).thenReturn(result);
        //when
    	ApiExampleWrapper testResult = newsApi.getNewsStoryByTopic(topic);
        //then
    	verify(monoResult, times(1)).block();
    	assertEquals(result, testResult);
    }

    @Test
    void itShouldFindStory(){
        //given
    	String topic = "test";
        String articleTitle = "Test Title";
        String articleContent = "Test content";
        String articleUrl = "Test URL";
        
        String message =
                articleTitle + " -\n"
                        + articleContent
                        + "\nFull article: " + articleUrl;
        
    	when(webClient.get()).thenReturn(requestHeadersUriSpec);
    	when(requestHeadersUriSpec.uri( (Function<UriBuilder, URI>) any()) ).thenReturn(requestHeadersSpec);
    	when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    	when(responseSpec.bodyToMono(ApiExampleWrapper.class)).thenReturn(monoResult);
    	when(monoResult.block()).thenReturn(mockResult);
    	
    	when(mockResult.getArticles()).thenReturn(mockArticles);
    	when(mockArticles.get(0)).thenReturn(mockArticle);
    	when(mockArticle.getTitle()).thenReturn(articleTitle);
    	when(mockArticle.getContent()).thenReturn(articleContent);
    	when(mockArticle.getUrl()).thenReturn(articleUrl);
        //when
    	String testResult = newsApi.findStory(topic);
        //then
    	verify(mockResult, times(1)).getArticles();
    	assertEquals(message, testResult);
    }


}