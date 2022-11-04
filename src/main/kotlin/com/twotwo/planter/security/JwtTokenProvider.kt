package com.twotwo.planter.security

import com.twotwo.planter.auth.exception.AuthenticateException
import com.twotwo.planter.user.service.UserDetailService
import com.twotwo.planter.user.service.UserService
import io.jsonwebtoken.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

@Component
class JwtTokenProvider(private val userDetailService: UserDetailService, private val jwtProperties: JwtProperties) {
    private var secretKey: String = Base64.getEncoder().encodeToString(jwtProperties.secretKey.toByteArray())
    private var tokenValidTime: Long = jwtProperties.validateTime

    fun createToken(userId: Long): String {
        val claims: Claims = Jwts.claims().setSubject("userId")
        claims["userId"] = userId
        val now = Date()
        return Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + tokenValidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun validation(token: String) : Boolean {
        val claims: Claims = getAllClaims(token)
        val exp: Date = claims.expiration
        return exp.after(Date())
    }

    fun parsePk(token: String): Int {
        val claims: Claims = getAllClaims(token)
        return claims["userId"] as Int
    }

    fun getAuthentication(userId: String): Authentication {
        val userDetails: UserDetails = userDetailService.loadUserByUsername(userId)
        return UsernamePasswordAuthenticationToken(userDetails, "", mutableListOf())
    }

    private fun getAllClaims(token: String): Claims {
        try {
            val response = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body
            return response
        } catch(exception: Throwable) {
            println(exception)
            throw exception
        }
        catch (expiredJwtException: ExpiredJwtException) {
            throw AuthenticateException("")
        } catch (unsupportedJwtException: UnsupportedJwtException) {
            throw AuthenticateException("")
        } catch (malformedJwtException: MalformedJwtException) {
            throw AuthenticateException("")
        } catch (signatureException: SignatureException) {
            throw AuthenticateException("")
        } catch (illegalArgumentException: IllegalArgumentException) {
            throw AuthenticateException("")
        }
    }
}