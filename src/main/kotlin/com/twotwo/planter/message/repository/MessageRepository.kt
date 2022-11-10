package com.twotwo.planter.message.repository

import com.twotwo.planter.message.domain.Message
import com.twotwo.planter.message.domain.MessageStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository: JpaRepository<Message, Long> {
    @Query("SELECT m.*\n" +
            "FROM message m\n" +
            "JOIN (\n" +
            "    SELECT MAX(message_id) AS max_id, plant_manager_id\n" +
            "    FROM message m\n" +
            "    WHERE user_id = :userId AND status = 'ACTIVE'\n" +
            "    GROUP BY plant_manager_id\n" +
            ") AS m_2\n" +
            "ON m.plant_manager_id = m_2.plant_manager_id AND m.message_id = m_2.max_id\n" +
            "ORDER BY max_id DESC;", nativeQuery = true)
    fun findAll(userId: Long): List<Message>
    fun findAllByUserIdAndPlantManagerIdOrderByCreatedAtDesc(userId: Long, plantManagerId: Long): List<Message>
    @Modifying
    @Query(value = "UPDATE message SET status = 'DELETED' WHERE user_id = :userId AND plant_manager_id = :plantManagerId", nativeQuery = true)
    fun updateStatus(userId: Long, plantManagerId: Long): Any
    fun findMessageById(messageId: Long): Message?
}