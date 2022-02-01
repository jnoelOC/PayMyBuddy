package com.paymybuddy.pmb.utils;

import java.io.Serializable;
import java.util.Properties;
import java.util.stream.Stream;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.exception.spi.Configurable;
import org.hibernate.id.IdentifierGenerator;

public class MyGenerator implements IdentifierGenerator, Configurable {

	private String prefix;

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {

		String query = String.format("select %s from %s",
				session.getEntityPersister(obj.getClass().getName(), obj).getIdentifierPropertyName(),
				obj.getClass().getSimpleName());

		Stream<?> ids = session.createQuery(query).stream();

		Long max = ids.map(o -> ((String) o).replace(prefix + "-", ""))
				// .mapToLong(g -> Long.parseLong(g))
				.mapToLong(Long::parseLong).max().orElse(0L);

		return prefix + "-" + (max + 1);
	}

	@Override
	public void configure(Properties properties) throws MappingException {
		prefix = properties.getProperty("prefix");
	}

}