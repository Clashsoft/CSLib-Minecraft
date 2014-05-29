package clashsoft.cslib.src.parser;

import clashsoft.cslib.src.SyntaxException;

public class FakeToken implements IToken
{
	private IToken prev;
	private IToken next;
	
	@Override
	public void setPrev(IToken token)
	{
		this.prev = token;
	}
	
	@Override
	public void setNext(IToken token)
	{
		this.next = token;
	}
	
	@Override
	public String value() throws SyntaxException
	{
		throw new SyntaxException("No token!");
	}

	@Override
	public int index() throws SyntaxException
	{
		throw new SyntaxException("No token!");
	}

	@Override
	public int start() throws SyntaxException
	{
		throw new SyntaxException("No token!");
	}

	@Override
	public int end() throws SyntaxException
	{
		throw new SyntaxException("No token!");
	}

	@Override
	public IToken next() throws SyntaxException
	{
		if (this.next == null)
			throw new SyntaxException("No next token!");
		return this.next;
	}

	@Override
	public IToken prev() throws SyntaxException
	{
		if (this.prev == null)
			throw new SyntaxException("No prev token!");
		return this.prev;
	}
}