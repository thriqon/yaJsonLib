
package de.jonasw.yajsonlib;

import java.util.*;
import junit.framework.*;
import java.io.*;

/*
 * JsonOutputStreamTest.java
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

public class JsonOutputStreamTest extends TestCase
{
	public JsonOutputStreamTest()
	{
		super();
	}

	class SomeClassWithToString
	{
		@Override
		public String toString()
		{
			return "SomeClassWithToString";
		}
	}

	public void testObject() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JsonOutputStream writer = new JsonOutputStream(baos);

		writer.writeObject(new SomeClassWithToString());
		writer.close();

		assertTrue(baos.toString("utf8").equals("'SomeClassWithToString'"));

		baos = new ByteArrayOutputStream();
		writer = new JsonOutputStream(baos);

		writer.writeObject(null); writer.close();
		assertTrue(baos.toString("utf8").equals("'NULL'"));
	}


	public void testArray() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JsonOutputStream writer = new JsonOutputStream(baos);

		Integer[] ints = {1,5,3,6,2,5,4,7,1,1};

		writer.writeObject(ints); writer.close();
		assertTrue(baos.toString("utf8").equals("[1,5,3,6,2,5,4,7,1,1]"));


		Integer[] nnints = {};
		baos = new ByteArrayOutputStream();
		writer = new JsonOutputStream(baos);
		writer.writeObject(nnints); writer.close();

		assertTrue(baos.toString("utf8").equals("[]"));
	}


	public void testIterable() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JsonOutputStream writer = new JsonOutputStream(baos);

		
		List<Integer> ints = Arrays.asList(new Integer(1),new Integer(5),new Integer(3),new Integer(6),new Integer(2),new Integer(5),new Integer(4),new Integer(7),new Integer(1),new Integer(1));

		writer.writeObject(ints); writer.close();
		assertTrue(baos.toString("utf8").equals("[1,5,3,6,2,5,4,7,1,1]"));


		Vector<Integer> nnints = new Vector<Integer>();
		baos = new ByteArrayOutputStream();
		writer = new JsonOutputStream(baos);
		writer.writeObject(nnints); writer.close();

		assertTrue(baos.toString("utf8").equals("[]"));

	}

	public void testMap() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JsonOutputStream writer = new JsonOutputStream(baos);

		Map m = new HashMap();
		m.put("key 1", "val 1");
		m.put("key 2", "val 2");
		m.put("key 3", new Integer(4711));
		m.put("key 4", null);

		writer.writeObject(m); writer.close();
		assertTrue(baos.toString("utf8").equals("{'key 1': 'val 1', 'key 2': 'val 2', 'key 3': 4711, 'key 4': 'NULL'}"));
	}
}

