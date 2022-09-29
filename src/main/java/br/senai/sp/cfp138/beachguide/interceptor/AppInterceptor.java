package br.senai.sp.cfp138.beachguide.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.senai.sp.cfp138.beachguide.annotation.Privado;
import br.senai.sp.cfp138.beachguide.annotation.Publico;
import br.senai.sp.cfp138.beachguide.rest.UsuarioRestController;

@Component
public class AppInterceptor implements HandlerInterceptor {
	
}
