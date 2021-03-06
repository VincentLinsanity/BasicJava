這個範例說明如何使用java的反射(Reflect)機制在執行期取得一個類別的成員(屬性及方法)
其中modifier包含了public/private/protected static final const等修飾詞
type表示 String/int/float/boolean等等型別
name表示屬性或方法的名稱
方法回傳的型別為Method、屬性回傳的型別為Field，也可以一次取得全部的成員或方法，以陣列回傳
另外當一個成員被宣告為private時，仍可利用反射機制取值/設值而不需要透過公開的getter/setter函式

// 介面屬性
public interface Attribute
{
	int hp = 0;

	String name = "";

	String id = "";

	public int getHp();

	public String getName();

	public String getId();
}

// 玩家屬性 實作介面屬性
public class UserAttribute implements Attribute
{
	private int userLevel;
	
	protected int userVip;
	
	public String userID;
	
	@Override
	public int getHp()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public int getUserLevel()
	{
		return userLevel;
	}

	public void setUserLevel(int userLevel)
	{
		this.userLevel = userLevel;
	}
}

// 反射機制邏輯
public class ReflectProcessor
{
	private static ReflectProcessor hInstance = null;

	public static ReflectProcessor getInstance()
	{
		if (hInstance == null)
		{
			hInstance = new ReflectProcessor();
		}
		return hInstance;
	}

	private ReflectProcessor()
	{

	}

	public Class<?> getClassByName(String pName) throws ClassNotFoundException
	{
		return Class.forName(pName);
	}

	public boolean isInterface(Class<?> c)
	{
		int m = c.getModifiers();
		return Modifier.isInterface(m);
	}

	public Field[] getFields(Class<?> c)
	{
		return c.getDeclaredFields();
	}

	public ArrayList<String> getFieldsList(Field[] f)
	{
		ArrayList<String> resultArray = new ArrayList<String>();
		for (Field field : f)
		{
			resultArray.add(field.getName());
		}
		return resultArray;
	}

	public void printFields(Class<?> c)
	{
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields)
		{
			System.out.print(Modifier.toString(field.getModifiers()) + " ");
			System.out.print(field.getType().getName() + " ");
			System.out.print(field.getName());
			System.out.println();
		}
	}

	public void printMethods(Class<?> c)
	{
		Method[] methods = c.getDeclaredMethods();
		for (Method method : methods)
		{
			System.out.print(Modifier.toString(method.getModifiers()) + " ");
			System.out.print(method.getReturnType().getName() + " ");
			System.out.print(method.getName());
			System.out.println();
		}
	}
}

// 簡單的測試主程式
public class Main
{
	public static void main(String[] args)
	{
		Class<?> c = null;
		try
		{
			c = ReflectProcessor.getInstance().getClassByName("xpec.vincent.reflect.userAttribute");
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		// 反射class的成員與方法
		ReflectProcessor.getInstance().printFields(c);
		ReflectProcessor.getInstance().printMethods(c);
	}
}

// 執行結果
private int userLevel
protected int userVip
public java.lang.String userID
public java.lang.String getName
public java.lang.String getId
public int getHp
public int getUserLevel
public void setUserLevel

此外反射機制還可以取得private成員的值，而不需要透過對外開放的getter/setter函式

UserAttribute userAttribute = new UserAttribute();
userAttribute.setUserLevel(10);
	
Field field = c.getDeclaredField("userLevel");
field.setAccessible(true);
Object value = field.get(userAttribute);
System.out.println(value);
field.set(userAttribute, 20);
System.out.println(userAttribute.getUserLevel());

// 執行結果
10
20
