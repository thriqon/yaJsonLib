
package de.jonasw.yajsonlib;

import java.util.*;
import java.io.*;

/*
 * JsonOutputStream.java
 *
 * Copyright (C) 2011 Jonas Weber
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl 5
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */

/**
 * exports Objects to another Stream.
 *
 * The following types are supported:
 *   * Arrays (Class.isArray() yields true)
 *   * Iterables (implements java.lang.Iterable)
 *   * Maps (implements java.util.Map).
 *
 *
 * It works by identifying the real classes
 * of the objects given. If an Object is
 * an Array, it is printed as [ content ].
 *
 * The output is sent to an OutputStream
 * set during object construction. It must
 * be writable.
 *
 * All IOExceptions that are thrown by
 * the substream are propagated upwards.
 *
 * @author Jonas Weber
 * @version 0.1
 *
 * Created: Thu Nov  3 22:00:10 CET 2011
 *
 */
public class JsonOutputStream extends OutputStream
{
	private OutputStream nextstream;


	/**
	 * constructs a new JsonOutputStream.
	 *
	 * @param nextstream The stream to which the output is sent.
	 * 
	 */
	public JsonOutputStream(OutputStream nextstream)
	{
		super();
		this.nextstream = nextstream;
	}

	/**
	 * write the object given to the stream in Json-Format.
	 */
	public void writeObject(Object a) throws IOException
	{
		if (a == null) { // NULL
			write("'NULL'".getBytes());

		} else if (a.getClass().isArray()) { // Array
			appendArray((Object[]) a);

		} else if (Map.class.isAssignableFrom(a.getClass())) { // Map
			appendMap((Map) a);

		} else if (Iterable.class.isAssignableFrom(a.getClass())) { // Iterable
			appendIterable((Iterable) a);

		} else if (Number.class.isAssignableFrom(a.getClass())) { // Number
			appendNumber((Number) a);

		} else { // String
			appendString(a);
		}
	}

	private void appendNumber(Number a) throws IOException
	{
		write(a.toString().getBytes());
	}

	private void appendString(Object a) throws IOException
	{
		write('\'');
		write(a.toString().getBytes());
		write('\'');
	}

	private void appendIterable(Iterable t) throws IOException
	{
		write('[');
		Iterator it = t.iterator();
		if (!it.hasNext()) // no elements, bail out
		{
			write(']');
			return;
		}

		while (it.hasNext())
		{
			Object o = it.next();
			writeObject(o);
			if (it.hasNext()) write(',');
		}
		write(']');
	}
	
	private void appendArray(Object[] a) throws IOException
	{
		write('[');
		if (a.length == 0)
		{
			write(']');
			return;
		}

		for (int i = 0; i < a.length - 1; ++i)
		{
			writeObject(a[i]);
			write(',');
		}
		writeObject(a[a.length - 1]);
		write(']');
	}

	private void appendMap(Map m) throws IOException
	{
		write('{');
		
		Object[] keys = m.keySet().toArray();

		for (int i = 0; i < keys.length; ++i)
		{
			write('\'');
			write(keys[i].toString().getBytes());
			write("\': ".getBytes());
			writeObject(m.get(keys[i]));
			if (m.keySet().size() - i > 1)
			{
				write(','); write(' ');
			}
		}
			
		write('}');
	}

	/** @inheritDoc */
	public void flush() throws IOException
	{
		nextstream.flush();
	}

	/** @inheritDoc */
	public void close() throws IOException
	{
		nextstream.close();
	}

	/** @inheritDoc */
	public void write(int b) throws IOException
	{
		nextstream.write(b);
	}
}

