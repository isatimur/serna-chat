package com.itime.sernachat.config

import com.itime.sernachat.service.RedisSubscriber
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
    @Bean
    fun channelTopic(): ChannelTopic {
        return ChannelTopic("chatroom")
    }

    @Bean
    fun redisMessageListenerContainer(
        factory: RedisConnectionFactory,
        listenerAdapter: MessageListenerAdapter,
        channelTopic: ChannelTopic
    ): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(factory)
        container.addMessageListener(listenerAdapter, channelTopic)
        return container
    }

    @Bean
    fun listenerAdapter(redisSubscriber: RedisSubscriber): MessageListenerAdapter {
        return MessageListenerAdapter(redisSubscriber, "sendMessage")
    }

    @Bean
    fun redisTemplate(factory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(factory)
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = Jackson2JsonRedisSerializer(String::class.java)
        return template
    }
}
