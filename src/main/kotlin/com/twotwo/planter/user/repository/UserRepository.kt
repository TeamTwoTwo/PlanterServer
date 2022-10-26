package com.twotwo.planter.user.repository

import com.twotwo.planter.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun save(user: User): User
    fun findByEmail(email: String): User
    fun existsByEmail(email: String): Boolean
    fun existsByPhone(phone: String): Boolean
    fun findByEmailAndPassword(email: String, password: String): User?
}