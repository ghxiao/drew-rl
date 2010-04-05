package DLV;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class Predicate {
	PredicateImplementation impl = null;
	private PredicateMetaData metadata = null;
	private String name;
	private int arity;
	private Model model = null;
	private int cursor = -1;
	private boolean insertRow = false;
	private Object[] termsBuffer = null;
	private Boolean signBuffer = null;
	private boolean modified = false;
	private int bufferedRow = -1;

	public Predicate(String paramString, int paramInt) {
		if (paramString == null)
			throw new NullPointerException("name cannot be null");
		this.name = paramString;
		this.arity = paramInt;
		this.impl = new InMemoryPI(paramInt);
	}

	public Predicate(String name, PredicateMetaData metaData) {
		if (name == null)
			throw new NullPointerException("name cannot be null");
		this.name = name;
		this.arity = metaData.getArity();
		this.metadata = metaData;
		this.impl = new InMemoryPI(this.arity);
	}

	Predicate(String paramString, int paramInt, Model paramModel) {
		if (paramString == null)
			throw new NullPointerException("name cannot be null");
		this.name = paramString;
		this.arity = paramInt;
		this.model = paramModel;
		this.impl = new InMemoryPI(paramInt);
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public void setPredicateMetaData(PredicateMetaData paramPredicateMetaData) {
		if (paramPredicateMetaData.getArity() != this.arity)
			throw new BadArityException("The specified metadata is invalid.");
		this.metadata = paramPredicateMetaData;
	}

	public PredicateMetaData getPredicateMetaData() {
		return this.metadata;
	}

	public String name() {
		return this.name;
	}

	public int arity() {
		return this.arity;
	}

	public int size() {
		return this.impl.size();
	}

	public Model getModel() {
		return this.model;
	}

	public String getTermAt(int paramInt1, int paramInt2) {
		if ((paramInt1 >= this.impl.size()) || (paramInt1 < 0))
			throw new NoSuchLiteralException();
		if (this.metadata == null)
			// return ConversionType.objectToString(this.impl.getTerm(paramInt1,
			// paramInt2), 2);
			return ConversionType.objectToString(this.impl.getTerm(paramInt1, paramInt2), ConversionType.STRING);
		return ConversionType.objectToString(this.impl.getTerm(paramInt1, paramInt2), this.metadata.getType(paramInt2 + 1));
	}

	public void setTermAt(int paramInt1, int paramInt2, String paramString) {
		if ((paramInt1 >= this.impl.size()) || (paramInt1 < 0))
			throw new NoSuchLiteralException();
		this.impl.setTerm(paramInt1, paramInt2, paramString);
		this.impl.update(paramInt1);
	}

	public Literal getLiteral() {
		return getLiteral(this.cursor);
	}

	public Literal getLiteral(int paramInt) {
		if ((paramInt >= this.impl.size()) || (paramInt < 0))
			throw new NoSuchLiteralException();
		String[] arrayOfString = new String[this.arity];
		if (this.metadata == null)
			for (int i = 0;; ++i) {
				if (i >= this.arity)
					// break label111;
					break;
				// arrayOfString[i] =
				// ConversionType.objectToString(this.impl.getTerm(paramInt, i),
				// 2);
				arrayOfString[i] = ConversionType.objectToString(this.impl.getTerm(paramInt, i), ConversionType.STRING);
			}
		for (int i = 0; i < this.arity; ++i)
			arrayOfString[i] = ConversionType.objectToString(this.impl.getTerm(paramInt, i), this.metadata.getType(i + 1));
		label111: return new Literal(arrayOfString, this.impl.getSign(paramInt));
	}

	public void updateLiteral(Literal paramLiteral) {
		updateLiteral(this.cursor, paramLiteral);
	}

	public void updateLiteral(int paramInt, Literal paramLiteral) {
		if (paramLiteral == null)
			throw new NullPointerException();
		if ((!this.impl.trueNegationSupported()) && (!paramLiteral.isPositive()))
			throw new TrueNegationNotSupportedException();
		if (paramLiteral.term.length != this.arity)
			throw new BadArityException();
		int i = this.impl.size();
		if ((i == 0) || (paramInt >= i) || (paramInt < 0))
			throw new NoSuchLiteralException();
		this.impl.setTerms(paramInt, paramLiteral.term);
		this.impl.setSign(paramInt, paramLiteral.positive);
		this.impl.update(paramInt);
	}

	public void removeLiteral() {
		removeLiteral(this.cursor);
	}

	public void removeLiteral(int paramInt) {
		int i = this.impl.size();
		if ((i == 0) || (paramInt >= i) || (paramInt < 0))
			return;
		this.impl.remove(paramInt);
		if ((this.cursor >= paramInt) && (this.cursor > 0))
			this.cursor -= 1;
		if (this.impl.size() != 0)
			return;
		this.cursor = -1;
	}

	public void addLiteral(Literal paramLiteral) {
		Literal localLiteral = paramLiteral;
		if ((!this.impl.trueNegationSupported()) && (!localLiteral.isPositive()))
			throw new TrueNegationNotSupportedException();
		if (paramLiteral == null)
			throw new NullPointerException();
		if (paramLiteral.term.length != this.arity)
			throw new BadArityException();
		this.impl.add(paramLiteral.term, paramLiteral.positive);
	}

	public boolean hasMoreLiterals() {
		if (this.impl.size() != 0)
			return this.cursor < this.impl.size() - 1;
		return false;
	}

	public Literal nextLiteral() {
		if (next())
			return getLiteral();
		throw new NoSuchLiteralException();
	}

	public Literal previousLiteral() {
		if (previous())
			return getLiteral();
		throw new NoSuchLiteralException();
	}

	public Literal firstLiteral() {
		first();
		return getLiteral();
	}

	public Literal lastLiteral() {
		last();
		return getLiteral();
	}

	public int getCursor() {
		return this.cursor;
	}

	public boolean first() {
		if (this.insertRow)
			moveToCurrentRow();
		if (this.impl.size() > 0) {
			this.cursor = 0;
			return true;
		}
		return false;
	}

	public boolean last() {
		if (this.insertRow)
			moveToCurrentRow();
		if (this.impl.size() > 0) {
			this.cursor = (this.impl.size() - 1);
			return true;
		}
		return false;
	}

	public void beforeFirst() {
		if (this.insertRow)
			moveToCurrentRow();
		this.cursor = -1;
	}

	public void afterLast() {
		if (this.insertRow)
			moveToCurrentRow();
		if (this.impl.size() <= 0)
			return;
		this.cursor = this.impl.size();
	}

	public boolean isFirst() {
		return (this.cursor == 0) && (this.impl.size() > 0);
	}

	public boolean isLast() {
		int i = this.impl.size();
		return (this.cursor == i - 1) && (i > 0);
	}

	public boolean relative(int paramInt) {
		int i = this.impl.size();
		if (i <= 0)
			return false;
		this.cursor += paramInt;
		if (this.cursor < -1)
			this.cursor = -1;
		if (this.cursor > i)
			this.cursor = i;
		return (this.cursor > -1) && (this.cursor < i);
	}

	public boolean previous() {
		this.cursor += -1;
		if (this.cursor < -1)
			this.cursor = -1;
		int i = this.impl.size();
		if (this.cursor > i)
			this.cursor = i;
		return (this.cursor > -1) && (this.cursor < i);
	}

	public boolean next() {
		int i = this.impl.size();
		if (this.cursor < -1) {
			this.cursor = -1;
			return false;
		}
		this.cursor += 1;
		if (this.cursor > i) {
			this.cursor = i;
			return false;
		}
		return (this.cursor > -1) && (this.cursor < i) && (i != 0);
	}

	public int getRow() {
		if (this.cursor == this.impl.size())
			return 0;
		return this.cursor + 1;
	}

	public boolean absolute(int paramInt) {
		if (this.insertRow)
			moveToCurrentRow();
		int i = this.impl.size();
		int j;
		if (paramInt < 0) {
			j = i + paramInt;
			if (j < 0)
				this.cursor = -1;
			else
				this.cursor = j;
		} else {
			j = paramInt - 1;
			if (j > i)
				this.cursor = i;
			else
				this.cursor = j;
		}
		return (this.cursor > -1) && (this.cursor < i);
	}

	public void saveTo(String paramString) throws IOException {
		PrintWriter localPrintWriter = new PrintWriter(new FileWriter(paramString));
		appendTo(localPrintWriter);
		localPrintWriter.close();
	}

	public void appendTo(PrintWriter paramPrintWriter) {
		if (this.impl.size() == 0)
			return;
		for (int i = 0; i < this.impl.size(); ++i) {
			if (!this.impl.getSign(i))
				paramPrintWriter.print("-");
			paramPrintWriter.print(this.name);
			for (int j = 0; j < this.arity; ++j) {
				if (j == 0)
					paramPrintWriter.print('(');
				paramPrintWriter.print(getTermAt(i, j));
				if (j == this.arity - 1)
					paramPrintWriter.print(')');
				else
					paramPrintWriter.print(',');
			}
			paramPrintWriter.println('.');
		}
	}

	public Enumeration getLiterals() {
		return new Enumeration() {
			private int pos = 0;

			public boolean hasMoreElements() {
				return this.pos < Predicate.this.impl.size();
			}

			public Object nextElement() {
				if (!hasMoreElements())
					throw new NoSuchElementException();
				return Predicate.this.getLiteral(this.pos);
			}
		};
	}

	public String toString() {
		StringBuffer localStringBuffer = new StringBuffer();
		for (int i = 0; i < this.impl.size(); ++i) {
			if (!this.impl.getSign(i))
				localStringBuffer.append("-");
			localStringBuffer.append(this.name);
			for (int j = 0; j < this.arity; ++j) {
				if (j == 0)
					localStringBuffer.append('(');
				localStringBuffer.append(getTermAt(i, j));
				if (j == this.arity - 1)
					localStringBuffer.append(')');
				else
					localStringBuffer.append(',');
			}
			localStringBuffer.append('.');
			localStringBuffer.append('\n');
		}
		return localStringBuffer.toString();
	}

	public boolean getIsPositive(int paramInt) {
		if ((paramInt >= this.impl.size()) || (paramInt < 0))
			throw new NoSuchLiteralException();
		return this.impl.getSign(paramInt);
	}

	public void setIsPositive(int paramInt, boolean paramBoolean) {
		if ((paramInt >= this.impl.size()) || (paramInt < 0))
			throw new NoSuchLiteralException();
		if ((!this.impl.trueNegationSupported()) && (!paramBoolean))
			throw new TrueNegationNotSupportedException();
		this.impl.setSign(paramInt, paramBoolean);
	}

	public boolean getIsPositive() {
		return getCurrentTermSign();
	}

	public void updateIsPositive(boolean paramBoolean) {
		if ((!this.impl.trueNegationSupported()) && (!paramBoolean))
			throw new TrueNegationNotSupportedException();
		setCurrentTermSign(new Boolean(paramBoolean));
	}

	public void updateBoolean(int paramInt, boolean paramBoolean) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		setCurrentTerm(i, new Boolean(paramBoolean));
	}

	public void updateBigDecimal(int paramInt, BigDecimal paramBigDecimal) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		setCurrentTerm(i, paramBigDecimal);
	}

	public void updateByte(int paramInt, byte paramByte) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		setCurrentTerm(i, new Byte(paramByte));
	}

	public void updateDate(int paramInt, Date paramDate) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		setCurrentTerm(i, paramDate);
	}

	public void updateDouble(int paramInt, double paramDouble) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		setCurrentTerm(i, new Double(paramDouble));
	}

	public void updateFloat(int paramInt, float paramFloat) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		setCurrentTerm(i, new Float(paramFloat));
	}

	public void updateInt(int paramInt1, int paramInt2) {
		if ((paramInt1 < 1) || (paramInt1 > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt1 - 1;
		setCurrentTerm(i, new Integer(paramInt2));
	}

	public void updateLong(int paramInt, long paramLong) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		setCurrentTerm(i, new Long(paramLong));
	}

	public void updateNull(int paramInt) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		setCurrentTerm(i, ConversionType.DBNULL_CLASS);
	}

	public void updateShort(int paramInt, short paramShort) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		setCurrentTerm(i, new Short(paramShort));
	}

	public void updateString(int paramInt, String paramString) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		setCurrentTerm(i, paramString);
	}

	public void updateTime(int paramInt, Time paramTime) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		setCurrentTerm(i, paramTime);
	}

	public boolean getBoolean(int paramInt) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		Object localObject = getCurrentTerm(i);
		if (localObject instanceof Boolean)
			return ((Boolean) localObject).booleanValue();
		if (localObject == ConversionType.DBNULL_CLASS)
			return false;
		throw new MalformedTermException();
	}

	public BigDecimal getBigDecimal(int paramInt) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		Object localObject = getCurrentTerm(i);
		if (localObject instanceof BigDecimal)
			return (BigDecimal) localObject;
		if (localObject == ConversionType.DBNULL_CLASS)
			return null;
		throw new MalformedTermException();
	}

	public byte getByte(int paramInt) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		Object localObject = getCurrentTerm(i);
		if (localObject instanceof Byte)
			return ((Byte) localObject).byteValue();
		if (localObject == ConversionType.DBNULL_CLASS)
			return 0;
		throw new MalformedTermException();
	}

	public Date getDate(int paramInt) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		Object localObject = getCurrentTerm(i);
		if (localObject instanceof Date)
			return (Date) localObject;
		if (localObject == ConversionType.DBNULL_CLASS)
			return null;
		throw new MalformedTermException();
	}

	public double getDouble(int paramInt) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		Object localObject = getCurrentTerm(i);
		if (localObject instanceof Double)
			return ((Double) localObject).doubleValue();
		if (localObject == ConversionType.DBNULL_CLASS)
			return 0.0D;
		throw new MalformedTermException();
	}

	public float getFloat(int paramInt) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		Object localObject = getCurrentTerm(i);
		if (localObject instanceof Float)
			return ((Float) localObject).floatValue();
		if (localObject == ConversionType.DBNULL_CLASS)
			return 0.0F;
		throw new MalformedTermException();
	}

	public int getInt(int paramInt) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		Object localObject = getCurrentTerm(i);
		if (localObject instanceof Integer)
			return ((Integer) localObject).intValue();
		if (localObject == ConversionType.DBNULL_CLASS)
			return 0;
		throw new MalformedTermException();
	}

	public long getLong(int paramInt) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		Object localObject = getCurrentTerm(i);
		if (localObject instanceof Long)
			return ((Long) localObject).longValue();
		if (localObject == ConversionType.DBNULL_CLASS)
			return 0L;
		throw new MalformedTermException();
	}

	public short getShort(int paramInt) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		Object localObject = getCurrentTerm(i);
		if (localObject instanceof Short)
			return ((Short) localObject).shortValue();
		if (localObject == ConversionType.DBNULL_CLASS)
			return 0;
		throw new MalformedTermException();
	}

	public String getString(int paramInt) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		Object localObject = getCurrentTerm(i);
		if (localObject instanceof String)
			return (String) localObject;
		if (localObject == ConversionType.DBNULL_CLASS)
			return null;
		throw new MalformedTermException();
	}

	public Time getTime(int paramInt) {
		if ((paramInt < 1) || (paramInt > this.arity))
			throw new NoSuchLiteralException();
		int i = paramInt - 1;
		Object localObject = getCurrentTerm(i);
		if (localObject instanceof Time)
			return (Time) localObject;
		if (localObject == ConversionType.DBNULL_CLASS)
			return null;
		throw new MalformedTermException();
	}

	private void setCurrentTerm(int paramInt, Object paramObject) {
		if ((this.bufferedRow != this.cursor) || (!this.modified)) {
			this.bufferedRow = this.cursor;
			this.termsBuffer = new Object[this.arity];
			this.termsBuffer = this.impl.getTerms(this.bufferedRow, this.termsBuffer);
			this.signBuffer = new Boolean(this.impl.getSign(paramInt));
			this.modified = true;
		}
		this.termsBuffer[paramInt] = paramObject;
	}

	private void setCurrentTermSign(Boolean paramBoolean) {
		if ((this.bufferedRow != this.cursor) || (!this.modified)) {
			this.bufferedRow = this.cursor;
			this.termsBuffer = new Object[this.arity];
			this.termsBuffer = this.impl.getTerms(this.bufferedRow, this.termsBuffer);
			this.modified = true;
		}
		this.signBuffer = paramBoolean;
	}

	private Object getCurrentTerm(int paramInt) {
		if ((this.insertRow) || ((this.modified) && (this.bufferedRow == this.cursor)))
			return this.termsBuffer[paramInt];
		return this.impl.getTerm(this.cursor, paramInt);
	}

	private boolean getCurrentTermSign() {
		if ((this.insertRow) || ((this.modified) && (this.bufferedRow == this.cursor)))
			return this.signBuffer.booleanValue();
		return this.impl.getSign(this.cursor);
	}

	public void moveToInsertRow() {
		this.insertRow = true;
		this.bufferedRow = -1;
		this.termsBuffer = new Object[this.arity];
		this.signBuffer = new Boolean(true);
		this.modified = true;
		for (int i = 0; i < this.arity; ++i)
			this.termsBuffer[i] = ConversionType.DBNULL_CLASS;
	}

	public void cancelRowUpdates() {
		if (this.insertRow)
			moveToInsertRow();
		this.termsBuffer = null;
		this.signBuffer = null;
		this.modified = false;
		this.bufferedRow = -1;
	}

	public void moveToCurrentRow() {
		this.insertRow = false;
		this.termsBuffer = null;
		this.signBuffer = null;
		this.modified = false;
		this.bufferedRow = -1;
	}

	public void insertRow() {
		if (this.insertRow) {
			this.impl.add(this.termsBuffer, this.signBuffer.booleanValue());
			moveToInsertRow();
		} else {
			throw new DLVExceptionUncheked("You must be on the insert row!");
		}
	}

	public void updateRow() {
		if (this.insertRow)
			throw new DLVExceptionUncheked("You cannot update the insert row!");
		if ((!this.modified) || (this.bufferedRow != this.cursor))
			return;
		this.impl.setTerms(this.bufferedRow, this.termsBuffer);
		this.impl.setSign(this.bufferedRow, this.signBuffer.booleanValue());
		this.impl.update(this.bufferedRow);
	}

	private class LiteralHandler {
		String[] term;
		boolean sign;

		// private final Predicate this$0;

		private LiteralHandler() {
			// this.this$0 = this$1;
			this.term = null;
			this.sign = true;
		}

		boolean equalTo(Object[] paramArrayOfObject, boolean paramBoolean) {
			if (paramBoolean != this.sign)
				return false;
			for (int i = 0; i < this.term.length; ++i) {
				String str;
				// if (this.this$0.metadata == null)
				if (Predicate.this.metadata == null)
					str = ConversionType.objectToString(paramArrayOfObject[i], ConversionType.STRING);
				else
					str = ConversionType.objectToString(paramArrayOfObject[i], Predicate.this.metadata.getType(i + 1));
				if (!str.equals(this.term[i]))
					return false;
			}
			return true;
		}

		// LiteralHandler(Predicate.1 arg2)
		// {
		// this(this$1);
		// }
	}

	class InMemoryPI extends PredicateImplementation {
		private ArrayList list = new ArrayList();

		InMemoryPI(int arg2) {
			super(arg2);
			// super(i);
		}

		void updateImplementation(int paramInt, Object[] paramArrayOfObject, Boolean paramBoolean) {
			if ((paramInt < 0) || (paramInt >= this.list.size()))
				throw new NoSuchLiteralException();
			// Predicate.LiteralHandler localLiteralHandler = new
			// Predicate.LiteralHandler(Predicate.this, null);
			Predicate.LiteralHandler localLiteralHandler = new Predicate.LiteralHandler();
			localLiteralHandler.term = new String[Predicate.this.arity];
			if (paramBoolean != null)
				localLiteralHandler.sign = paramBoolean.booleanValue();
			if (paramArrayOfObject != null) {
				if (Predicate.this.metadata == null)
					for (int i = 0;; ++i) {
						if (i >= Predicate.this.arity)
							break;// label174;
						localLiteralHandler.term[i] = ConversionType.objectToString(paramArrayOfObject[i], ConversionType.STRING);
					}
				for (int i = 0;; ++i) {
					if (i >= Predicate.this.arity)
						break;// label174;
					// localLiteralHandler.term[i] =
					// ConversionType.objectToString(paramArrayOfObject[i],
					// Predicate.access$500(Predicate.this).getType(i + 1));
					localLiteralHandler.term[i] = ConversionType.objectToString(paramArrayOfObject[i], //
							Predicate.this.metadata.getType(i + 1));
				}
			}
			//throw new NullPointerException();
			 label174: this.list.remove(paramInt);
			 this.list.add(paramInt, localLiteralHandler);
		}

		Object getTerm(int paramInt1, int paramInt2) {
			if ((paramInt1 < 0) || (paramInt1 >= this.list.size()))
				throw new NoSuchLiteralException();
			if ((paramInt2 < 0) || (paramInt2 >= Predicate.this.arity))
				throw new NoSuchTermException();
			Predicate.LiteralHandler localLiteralHandler = (Predicate.LiteralHandler) this.list.get(paramInt1);
			if (Predicate.this.metadata == null)
				return ConversionType.stringToObject(localLiteralHandler.term[paramInt2], ConversionType.STRING);
			return ConversionType.stringToObject(localLiteralHandler.term[paramInt2], Predicate.this.metadata.getType(paramInt2 + 1));
		}

		boolean getSign(int paramInt) {
			if ((paramInt < 0) || (paramInt >= this.list.size()))
				throw new NoSuchLiteralException();
			Predicate.LiteralHandler localLiteralHandler = (Predicate.LiteralHandler) this.list.get(paramInt);
			return localLiteralHandler.sign;
		}

		int size() {
			return this.list.size();
		}

		void removeFromImplementaion(int paramInt) {
			if ((paramInt < 0) || (paramInt >= this.list.size()))
				throw new NoSuchLiteralException();
			this.list.remove(paramInt);
		}

		int indexOf(Object[] paramArrayOfObject, boolean paramBoolean) {
			for (int i = 0; i < this.list.size(); ++i) {
				Predicate.LiteralHandler localLiteralHandler = (Predicate.LiteralHandler) this.list.get(i);
				if (localLiteralHandler.equalTo(paramArrayOfObject, paramBoolean))
					return i;
			}
			return -1;
		}

		void add(Object[] paramArrayOfObject, boolean paramBoolean) {
			Predicate.LiteralHandler localLiteralHandler = new Predicate.LiteralHandler();
			localLiteralHandler.sign = paramBoolean;
			localLiteralHandler.term = new String[Predicate.this.arity];
			if (paramArrayOfObject != null) {
				if (Predicate.this.metadata == null)
					for (int i = 0;; ++i) {
						if (i >= Predicate.this.arity)
							break;// label139;
						localLiteralHandler.term[i] = ConversionType.objectToString(paramArrayOfObject[i], ConversionType.STRING);
					}
				for (int i = 0;; ++i) {
					if (i >= Predicate.this.arity)
						break;// label139;
					localLiteralHandler.term[i] = ConversionType.objectToString(paramArrayOfObject[i],// 
							Predicate.this.metadata.getType(i + 1));
				}
			}
			//throw new NullPointerException();
			label139: this.list.add(localLiteralHandler);
		}
	}

	public class Literal {
		private String[] term = new String[0];
		private boolean positive = true;
		private String[] oldTerm = null;
		private boolean oldPositive = true;

		private void bufferTerm() {
			this.oldTerm = new String[Predicate.this.arity];
			for (int i = 0; i < Predicate.this.arity; ++i)
				this.oldTerm[i] = this.term[i];
			this.oldPositive = this.positive;
		}

		public Literal() {
			if (Predicate.this.arity != 0)
				throw new BadArityException();
			this.term = new String[0];
		}

		public Literal(boolean positive) {
			if (Predicate.this.arity != 0)
				throw new BadArityException();
			this.term = new String[0];
			int i;
			this.positive = positive;
		}

		public Literal(String[] term) {
			//Object localObject;
			if (term.length != Predicate.this.arity)
				throw new BadArityException();
			int i = 0;
			for (int j = 0; (i == 0) && (j < Predicate.this.arity); ++j) {
				if (term[j] != null)
					continue;
				i = 1;
			}
			if (i != 0)
				throw new NullPointerException("term contains a null String object");
			this.term = term;
		}

		public Literal(String[] paramBoolean, boolean positive) {
			if (paramBoolean.length != Predicate.this.arity)
				throw new BadArityException();
			int j = 0;
			for (int k = 0; (j == 0) && (k < Predicate.this.arity); ++k) {
				if (paramBoolean[k] != null)
					continue;
				j = 1;
			}
			if (j != 0)
				throw new NullPointerException("term contains a null String object");
			this.term = paramBoolean;
			int i;
			this.positive = positive;
		}

		public Predicate getPredicate() {
			return Predicate.this;
		}

		public String name() {
			return Predicate.this.name;
		}

		public int arity() {
			return Predicate.this.arity;
		}

		public String getTermAt(int paramInt) {
			if ((paramInt >= this.term.length) || (paramInt < 0))
				throw new NoSuchTermException();
			return this.term[paramInt];
		}

		/** @deprecated */
		public void setTermAt(String paramString, int paramInt) {
			setTermAt(paramInt, paramString);
		}

		public void setTermAt(int paramInt, String paramString) {
			if ((paramInt >= this.term.length) || (paramInt < 0))
				throw new NoSuchTermException();
			if (paramString == null)
				throw new NullPointerException();
			if ((!this.term[paramInt].equals(paramString)) && (this.oldTerm == null))
				bufferTerm();
			this.term[paramInt] = paramString;
		}

		public boolean isPositive() {
			return this.positive;
		}

		public void invert() {
			if (this.oldTerm == null)
				bufferTerm();
			this.oldPositive = this.positive;
			this.positive = (!this.positive);
		}

		public Literal getContrary() {
			return new Literal(this.term, !this.positive);
		}

		public boolean isContrary(Literal paramLiteral) {
			if (paramLiteral == null)
				throw new NullPointerException();
			if (paramLiteral == this)
				return false;
			if (paramLiteral.getPredicate() != Predicate.this)
				return false;
			if (this.positive == paramLiteral.positive)
				return false;
			if (this.term.length != paramLiteral.term.length)
				return false;
			for (int i = 0; i < this.term.length; ++i)
				if (this.term[i] != paramLiteral.term[i])
					return false;
			return true;
		}

		public String toString() {
			StringBuffer localStringBuffer = new StringBuffer();
			if (!this.positive)
				localStringBuffer.append("-");
			localStringBuffer.append(Predicate.this.name);
			for (int i = 0; i < Predicate.this.arity; ++i) {
				if (i == 0)
					localStringBuffer.append('(');
				localStringBuffer.append(this.term[i]);
				if (i == Predicate.this.arity - 1)
					localStringBuffer.append(')');
				else
					localStringBuffer.append(',');
			}
			localStringBuffer.append('.');
			return localStringBuffer.toString();
		}

		public void appendTo(PrintWriter paramPrintWriter) {
			if (!this.positive)
				paramPrintWriter.print("-");
			paramPrintWriter.print(Predicate.this.name);
			for (int i = 0; i < Predicate.this.arity; ++i) {
				if (i == 0)
					paramPrintWriter.print('(');
				paramPrintWriter.print(this.term[i]);
				if (i == Predicate.this.arity - 1)
					paramPrintWriter.print(')');
				else
					paramPrintWriter.print(',');
			}
			paramPrintWriter.print('.');
		}

		public boolean equals(Object paramObject) {
			Literal localLiteral = (Literal) paramObject;
			if (localLiteral == null)
				throw new NullPointerException();
			if (localLiteral == this)
				return true;
			if (localLiteral.getPredicate() != Predicate.this)
				return false;
			if (this.positive != localLiteral.positive)
				return false;
			if ((Predicate.this.arity == 0) && (this.positive == localLiteral.positive))
				return true;
			if (this.term == localLiteral.term)
				return true;
			if (this.term.length != localLiteral.term.length)
				return false;
			for (int i = 0; i < this.term.length; ++i)
				if (!this.term[i].equals(localLiteral.term[i]))
					return false;
			return true;
		}

		public void update() {
			if ((!Predicate.this.impl.trueNegationSupported()) && (!this.positive))
				throw new TrueNegationNotSupportedException();
			int i = -1;
			if (this.oldTerm == null)
				i = Predicate.this.impl.indexOf(this.term, this.positive);
			else
				i = Predicate.this.impl.indexOf(this.oldTerm, this.oldPositive);
			if (i == -1)
				throw new LiteralStaledException();
			Predicate.this.impl.setTerms(i, this.term);
			Predicate.this.impl.setSign(i, this.positive);
			Predicate.this.impl.update(i);
		}
	}
}

/*
 * Location: G:\OntoRule\implementations\LDL\lib\DLV3_0beta4.jar Qualified Name:
 * DLV.Predicate JD-Core Version: 0.5.4
 */