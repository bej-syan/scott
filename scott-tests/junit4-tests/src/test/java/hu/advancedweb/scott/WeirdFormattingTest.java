package hu.advancedweb.scott;

import static hu.advancedweb.scott.TestHelper.wrapped;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Ignore;
import org.junit.Test;

import hu.advancedweb.scott.MockitoTest.Foo;
import hu.advancedweb.scott.MockitoTest.MockHolder;

/**
 * Note that this file is sensitive to formatting.
 * 
 * @author David Csakvari
 */
public class WeirdFormattingTest {
	
	@Test
	public void simpleFormatting() throws Exception {
		String outer = "outer1";
		assertThat(TestHelper.getLastRecordedStateForVariable("outer"), equalTo(wrapped(outer)));
		{
			String inner = "inner1";
			assertThat(TestHelper.getLastRecordedStateForVariable("inner"), equalTo(wrapped(inner)));
		}
		outer = "outer2";
		assertThat(TestHelper.getLastRecordedStateForVariable("outer"), equalTo(wrapped(outer)));
	}
	
	@Test
	public void inlineFormatting_1() throws Exception {
		String outer = "outer1";
		assertThat(TestHelper.getLastRecordedStateForVariable("outer"), equalTo(wrapped(outer)));
		{String inner = "inner1"; assertThat(TestHelper.getLastRecordedStateForVariable("inner"), equalTo(wrapped(inner))); }
		outer = "outer2";
		assertThat(TestHelper.getLastRecordedStateForVariable("outer"), equalTo(wrapped(outer)));
	}
	
	@Test
	public void inlineFormatting_2() throws Exception {
		String outer = "outer1"; assertThat(TestHelper.getLastRecordedStateForVariable("outer"), equalTo(wrapped(outer))); {
			String inner = "inner1";
			assertThat(TestHelper.getLastRecordedStateForVariable("inner"), equalTo(wrapped(inner)));
		}
		outer = "outer2";
		assertThat(TestHelper.getLastRecordedStateForVariable("outer"), equalTo(wrapped(outer)));
	}

	@Test
	public void inlineFormatting_3() throws Exception {
		String outer = "outer1"; 
		assertThat(TestHelper.getLastRecordedStateForVariable("outer"), equalTo(wrapped(outer)));
		{ String inner = "inner1"; assertThat(TestHelper.getLastRecordedStateForVariable("inner"), equalTo(wrapped(inner))); } outer = "outer2"; assertThat(TestHelper.getLastRecordedStateForVariable("outer"), equalTo(wrapped(outer)));
	}
	
	@Test
	public void inlineFormatting_4() throws Exception {
		String outer = "outer1"; assertThat(TestHelper.getLastRecordedStateForVariable("outer"), equalTo(wrapped(outer))); { String inner = "inner1"; assertThat(TestHelper.getLastRecordedStateForVariable("inner"), equalTo(wrapped(inner))); } outer = "outer2"; assertThat(TestHelper.getLastRecordedStateForVariable("outer"), equalTo(wrapped(outer)));
	}
	
	@SuppressWarnings("null")
	@Test
	public void simpleFormattingWithTryCatch() {
		String o = null;
		
		try {
			String inner = "inner";
			assertThat(TestHelper.getLastRecordedStateForVariable("inner"), equalTo(wrapped(inner)));
			o.length();
		} catch (Exception e) {
			o = "fallback";
			assertThat(TestHelper.getLastRecordedStateForVariable("e"), equalTo(e.toString()));
			assertThat(TestHelper.getLastRecordedStateForVariable("o"), equalTo(wrapped(o)));
		}	
	}

	@SuppressWarnings("null")
	@Test
	@Ignore // FIXME: See issue 19: multiple variable declarations in different scopes on the same line cause problems.
	public void inlineFormattingWithTryCatch() {
		String o = null; try { String inner = "inner";	assertThat(TestHelper.getLastRecordedStateForVariable("inner"), equalTo(inner)); o.length(); } catch (Exception e) { o = "fallback"; assertThat(TestHelper.getLastRecordedStateForVariable("e"), equalTo(e.toString())); assertThat(TestHelper.getLastRecordedStateForVariable("o"), equalTo(o)); }	
	}
	
	@SuppressWarnings("null")
	@Test
	public void expandedFormattingWithTryCatch() {
		String o = null;
		
		try 
		{
			String inner = "inner";
			assertThat(TestHelper.getLastRecordedStateForVariable("inner"), equalTo(wrapped(inner)));
			o.length();
		}
		catch (Exception e)
		{
			o = "fallback";
			assertThat(TestHelper.getLastRecordedStateForVariable("e"), equalTo(e.toString()));
			assertThat(TestHelper.getLastRecordedStateForVariable("o"), equalTo(wrapped(o)));
		}	
	}

	@Test
	public void weirdFormattedMockitoTest() throws Exception {
		Foo foo = mock(
				Foo.class
				);
		MockHolder<Foo> holder = new MockHolder<>(foo);
		when(
				foo
				.bar())
		.thenReturn("42");

		String result = foo.bar();
		assertThat(TestHelper.getLastRecordedStateForVariable("result"), equalTo(wrapped("42")));

		assertEquals(
				"42",
				result);
		verify(
				foo,
				times(1))
		.bar();
		verify(
				holder.t,
				times(1))
		.bar();
	}
}
