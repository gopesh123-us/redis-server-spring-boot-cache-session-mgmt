package live.learnjava.cachingsession.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import live.learnjava.cachingsession.model.UserSessionModel;

@Configuration
public class RedisConfig {

	@Bean
	RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		template.setConnectionFactory(connectionFactory);
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.activateDefaultTyping(
        	    objectMapper.getPolymorphicTypeValidator(), 
        	    ObjectMapper.DefaultTyping.NON_FINAL
        	);
        Jackson2JsonRedisSerializer<UserSessionModel> serializer = new Jackson2JsonRedisSerializer<>(objectMapper, UserSessionModel.class);
       
		// use String serializer for keys
		template.setKeySerializer(new StringRedisSerializer());

		// use JSON serializer for values
		template.setValueSerializer(serializer);
		template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

		return template;
	}

	@Bean
	CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair
						.fromSerializer(new Jackson2JsonRedisSerializer<>(Object.class)));

		return RedisCacheManager.builder(redisTemplate.getConnectionFactory()).cacheDefaults(cacheConfig).build();
	}

}
