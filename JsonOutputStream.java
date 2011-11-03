
import java.util.*;
import java.io.*;


/**
 * exports Objects to another Writer.
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
 */
public class JsonOutputStream extends OutputStream
{
	private OutputStream nextstream;
	public JsonOutputStream(OutputStream nextstream)
	{
		super();
		this.nextstream = nextstream;
	}

	public void writeObject(Object a) throws IOException
	{
		if (a == null)
		{
			write("'NULL'".getBytes());
			return;
		}
		if (a.getClass().isArray())
		{
			appendArray((Object[]) a);
			return;
		}
		if (Map.class.isAssignableFrom(a.getClass())) 
		{
			appendMap((Map) a);
			return;
		}
		if (Iterable.class.isAssignableFrom(a.getClass()))
		{
			appendIterable((Iterable) a);
			return;
		}

		appendString(a);
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

	public void flush() throws IOException
	{
		nextstream.flush();
	}

	public void close() throws IOException
	{
		nextstream.close();
	}


	public void write(int b) throws IOException
	{
		nextstream.write(b);
	}
}

