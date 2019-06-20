package com.ws.spring.repository;

import org.springframework.session.Session;

public interface SessionRepository<S extends Session> {
	S createSession();

	void save(S session);

	S findById(String id);

	void deleteById(String id);

}
