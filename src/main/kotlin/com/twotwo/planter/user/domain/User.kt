package com.twotwo.planter.user.domain

import com.twotwo.planter.util.BaseTime
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "UserInfo")
class User(name: String, email: String, password: String, birth: String, phone: String, address: String, detailAddress: String?, latitude: Double?, longitude: Double?): BaseTime(), UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var name: String = name

    @Column(nullable = false, unique = true)
    var email: String = email

    @Column(nullable = false)
    private var password: String = password

    @Column(nullable = false)
    var birth: String = birth

    @Column(nullable = false)
    var phone: String = phone

    @Column(nullable = false)
    var address: String = address

    @Column
    var detailAddress: String? = detailAddress

    @Column
    var latitude: Double? = latitude

    @Column
    var longitude: Double? = longitude

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}