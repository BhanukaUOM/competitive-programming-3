package chap4.graph.sssp.dijkstra.hard.p11492;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Main {
	/**
	 * @param args
	 */
	static InputReader in 		= new InputReader(System.in);
    //static OutputWriter out	=	new OutputWriter(System.out);
	static BufferedReader br=null;

	public static void main(String[] args) throws Exception  {
		br=new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			try{
					if(process()==0) {
						System.out.print(sb);
						break;
					}
			}
			catch(Exception e) {
				//System.out.print(sb);
				//throw e;
				return;
			}
            
            
		}
		//out.flush();
		//out.close();
	}
	
	static int casen = 1;
	static StringBuffer sb = new StringBuffer();
	static int process() throws Exception{
		int n = in.readInt();
		if(n == 0)
			return n;
		Map<String, Integer> mp = new HashMap<>();
		Map<Integer, List<IntegerTriple>> conn = new HashMap<>();
		String start = in.readString(), end = in.readString();
		addWord(mp, conn, start);
		addWord(mp, conn, end);
		for(int i = 0; i < n; i++) {
			String st = in.readString(), en = in.readString(), word = in.readString();
			addWord(mp, conn, st);
			addWord(mp, conn, en);
			addConn(mp.get(st), mp.get(en), word, conn);
		}
		int st = mp.get(start), en = mp.get(end);
		PriorityQueue<IntegerTriple> pq = new PriorityQueue<>(1000, new Comparator<IntegerTriple>() {

			@Override
			public int compare(IntegerTriple o1, IntegerTriple o2) {
				return o1.c - o2.c;
			}
		});
		int[][] ans = new int[mp.size()][26];
		for(int i = 0; i < ans.length; i++)
			Arrays.fill(ans[i], -1);
		ans[st][0] = 0;
		pq.add(new IntegerTriple(st, -1, 0));
		int res = -1;
		while(pq.size() > 0) {
			IntegerTriple it = pq.remove();
			//System.out.println(pq.size());

			if(it.a == en) {
				res = it.c;
				break;
			}
			for(IntegerTriple c : conn.get(it.a)) {
				int cost = it.c + c.c;
				if(c.b != it.b && (ans[c.a][c.b] == -1 || ans[c.a][c.b] > cost )) {
					ans[c.a][c.b] = cost;
					pq.add(new IntegerTriple(c.a, c.b, cost));
				}
			}
		}
		//System.out.println(conn.get(mp.get("a")));
		if(res == -1) {
			System.out.println("impossivel");
		}
		else {
			System.out.println(res);
		}
		return 1;
	}
	static void addWord(Map<String, Integer> mp, Map<Integer, List<IntegerTriple>> conn, String w) {
		if(!mp.containsKey(w)) {
			List<IntegerTriple> costs = new ArrayList<>();
			int ind = mp.size();
			mp.put(w, mp.size());
			conn.put(ind, costs);
		}
	}
	
	static void addConn(int a, int b, String c, Map<Integer, List<IntegerTriple>> conn) {
		conn.get(a).add(new IntegerTriple(b, c.charAt(0) - 'a', c.length()));
		conn.get(b).add(new IntegerTriple(a, c.charAt(0) - 'a', c.length()));
	}
	
	public static class IntegerPair {
		int a;
		int b;
		public IntegerPair(int a, int b) {
			this.a = a;
			this.b = b;
		}
	}
	
	public static class IntegerTriple {
		int a;
		int b;
		int c;
		public IntegerTriple(int a, int b, int c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}
	}
	
	static String readLine(){
		try {
			return br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	static int[] giveArray(String[] str) {
		int[] ret = new int[str.length];
		for(int i = 0; i < str.length; i++) {
			if(str[i].compareTo("") != 0)
				ret[i] = Integer.parseInt(str[i].trim());
		}
		return ret;
	}
	static List<Integer> giveArrayList(String[] str) {
		List<Integer> ret = new ArrayList<>();
		for(int i = 0; i < str.length; i++) {
			if(str[i].compareTo("") != 0)
				ret.add(Integer.parseInt(str[i].trim()));
		}
		return ret;
	}
	
	static int findClosest(List<Integer> arr, int val, boolean comp, int s, int e) {
		//System.out.println(s+" : "+e+" : "+val+ " : "+Arrays.toString(arr));
		if(s == -1 || e == -1 || s > e)
			return -1;
		if(arr.size() == 0)
			return -1;
		if(comp && arr.get(s) > val) {
			return -1;
		}
		
		if(!comp && arr.get(e) < val) {
			return -1;
		}
		
		if(s == e) {
			return s;
		}
		
		if((e - s) == 1) {
			if(comp) {
				if(arr.get(e) <= val) {
					return e;
				}
				else {
					return s;
				}
			}
			else {
				if(arr.get(s) >= val) {
					return s;
				}
				else {
					return e;
				}
			}
		}
		int mid = (s + e) / 2;
		if(comp) {
			if(arr.get(mid) > val) {
				return findClosest(arr, val, comp, s, mid);
			}
			else {
				return findClosest(arr, val, comp, mid, e);
			}
		}
		else {
			if(arr.get(mid) < val) {
				return findClosest(arr, val, comp, mid, e);
			}
			else {
				return findClosest(arr, val, comp, s, mid);
			}
		}
	}
	static int findClosest(int[] arr, int val, boolean comp, int s, int e) {
		//System.out.println(s+" : "+e+" : "+val+ " : "+Arrays.toString(arr));
		if(s == -1 || e == -1 || s > e)
			return -1;
		if(arr.length == 0)
			return -1;
		if(comp && arr[s] > val) {
			return -1;
		}
		
		if(!comp && arr[e] < val) {
			return -1;
		}
		
		if(s == e) {
			return s;
		}
		
		if((e - s) == 1) {
			if(comp) {
				if(arr[e] <= val) {
					return e;
				}
				else {
					return s;
				}
			}
			else {
				if(arr[s] >= val) {
					return s;
				}
				else {
					return e;
				}
			}
		}
		int mid = (s + e) / 2;
		if(comp) {
			if(arr[mid] > val) {
				return findClosest(arr, val, comp, s, mid);
			}
			else {
				return findClosest(arr, val, comp, mid, e);
			}
		}
		else {
			if(arr[mid] < val) {
				return findClosest(arr, val, comp, mid, e);
			}
			else {
				return findClosest(arr, val, comp, s, mid);
			}
		}
	}
	static int findClosest(int[] arr, int val, boolean comp) {
		return findClosest(arr, val, comp, 0, arr.length - 1);
	}
	static int findClosest(List<Integer> arr, int val, boolean comp) {
		return findClosest(arr, val, comp, 0, arr.size() - 1);
	}
	
}
class InputReader {

	private InputStream stream;
	private byte[] buf = new byte[1024];
	private int curChar;
	private int numChars;
	private SpaceCharFilter filter;

	public InputReader(InputStream stream) {
		this.stream = stream;
	}

	public int read() {
		if (numChars == -1)
			throw new InputMismatchException();
		if (curChar >= numChars) {
			curChar = 0;
			try {
				numChars = stream.read(buf);
			} catch (IOException e) {
				throw new InputMismatchException();
			}
			if (numChars <= 0)
				return -1;
		}
		return buf[curChar++];
	}

	public int readInt() {
		int c = read();
		while (isSpaceChar(c))
			c = read();
		int sgn = 1;
		if (c == '-') {
			sgn = -1;
			c = read();
		}
		int res = 0;
		do {
			if (c < '0' || c > '9')
				throw new InputMismatchException();
			res *= 10;
			res += c - '0';
			c = read();
		} while (!isSpaceChar(c));
		return res * sgn;
	}

	public String readString() {
		int c = read();
		while (isSpaceChar(c))
			c = read();
		StringBuilder res = new StringBuilder();
		do {
			res.appendCodePoint(c);
			c = read();
		} while (!isSpaceChar(c));
		return res.toString();
	}

	public boolean isSpaceChar(int c) {
		if (filter != null)
			return filter.isSpaceChar(c);
		return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
	}

	public String next() {
		return readString();
	}

	public interface SpaceCharFilter {
		public boolean isSpaceChar(int ch);
	}
}

class OutputWriter {
	private final PrintWriter writer;

	public OutputWriter(OutputStream outputStream) {
		writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
	}

	public OutputWriter(Writer writer) {
		this.writer = new PrintWriter(writer);
	}

	public void print(Object...objects) {
		for (int i = 0; i < objects.length; i++) {
			if (i != 0)
				writer.print(' ');
			writer.print(objects[i]);
		}
	}

	public void printLine(Object...objects) {
		print(objects);
		writer.println();
	}

	public void close() {
		writer.close();
	}

	public void flush() {
		writer.flush();
	}

	}

class IOUtils {

	public static int[] readIntArray(InputReader in, int size) {
		int[] array = new int[size];
		for (int i = 0; i < size; i++)
			array[i] = in.readInt();
		return array;
	}
}

class UnionFind {
	int[] arr;
	public UnionFind(int size) {
		this.arr = new int[size];
		for(int i = 0; i < size; i++)
			this.arr[i] = i;
	}
	
	public void add(int a, int b) {
		while(arr[a] != a) { 
			a = arr[a];
		}
		while(arr[b] != b) {
			b = arr[b];
		}
		if(a > b) {
			int t = a;
			a = b;
			b = t;
		}
		arr[b] = a;
	}
	
	public int rootOf(int a) {
		int ta = a;
		
		while(arr[a] != a)
			a = arr[a];
		
		return a;
	}
	
	public boolean areConnected(int a, int b) {
		return rootOf(a) == rootOf(b);
	}
	
	public boolean disconnect(int a, int b) {
		if(arr[a] == b) {
			arr[a] = a;
			return true;
		}
		else if(arr[b] == a) {
			arr[b] = b;
			return false;
		}
		else return false;
	}
}