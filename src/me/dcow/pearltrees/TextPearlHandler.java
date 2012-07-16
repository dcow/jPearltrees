/*
 * Copyright 2012 David Cowden

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.dcow.pearltrees;

import java.io.PrintStream;


public class TextPearlHandler implements PearlHandler {

	private PrintStream outps;
	private String prefix		= "";
	private String prefixInc	= "\t";
	private String pearlPrefix 	= "* ";
	private String rootPrefix	= "# ";
	private String aliasPrefix	= "@ ";
	private String refPrefix	= "> ";
	
	protected TextPearlHandler(PrintStream outputStream) {
		this.outps = outputStream;
	}
	
	protected TextPearlHandler(PrintStream outputStream, String prefix) {
		this(outputStream);
		this.prefix = prefix;

	}
	
	protected TextPearlHandler(PrintStream outputStream, String prefix, 
			String pearlPrefix, String rootPrefix, String aliasPrefix, String refPrefix) {
		this(outputStream, prefix);
		this.pearlPrefix 	= pearlPrefix;
		this.rootPrefix 	= rootPrefix;
		this.aliasPrefix	= aliasPrefix;
		this.refPrefix 		= refPrefix;
		
	}
	
	protected TextPearlHandler(PrintStream outputStream, String prefix, String prefixInc,
			String pearlPrefix, String rootPrefix, String aliasPrefix, String refPrefix) {
		this(outputStream, prefix, pearlPrefix, rootPrefix, aliasPrefix, refPrefix);
		this.prefixInc 	= prefixInc; 
		
	}
	
	
	@Override
	public void onPearl(RootPearl rootPearl) {
		outps.println(prefix + rootPrefix + rootPearl.getTitle());
	}
	
	@Override
	public void onPearl(PagePearl pagePearl) {
		outps.println(prefix + prefixInc + pearlPrefix + pagePearl.getTitle());
	}
	
	@Override
	public void onPearl(AliasPearl aliasPearl) {
		outps.println(prefix + prefixInc + aliasPrefix + aliasPearl.getTitle());
	}
	
	@Override
	public void onPearl(RefPearl refPearl) {
		outps.println(prefix + prefixInc + refPrefix + refPearl.getTitle());
	}

	
	// ### Recursive version..
	
	public static class RecursiveTextPearlHandler extends TextPearlHandler {
		

		protected RecursiveTextPearlHandler(PrintStream outputStream) {
			super(outputStream);
		}
		
		protected RecursiveTextPearlHandler(PrintStream outputStream, String prefix) {
			super(outputStream, prefix);

		}
		
		protected RecursiveTextPearlHandler(PrintStream outputStream, String prefix, 
				String pearlPrefix, String rootPrefix, String aliasPrefix, String refPrefix) {
			super(outputStream, prefix, pearlPrefix, rootPrefix, aliasPrefix, refPrefix);
			
		}
		
		protected RecursiveTextPearlHandler(PrintStream outputStream, String prefix, String prefixInc,
				String pearlPrefix, String rootPrefix, String aliasPrefix, String refPrefix) {
			super(outputStream, prefix, prefixInc, pearlPrefix, rootPrefix, aliasPrefix, refPrefix);
			
		}
		
		@Override
		public void onPearl(RefPearl refPearl) {
			
			Pearltrees.traversePearlTree(refPearl.getPearlTree(), 
					new RecursiveTextPearlHandler(super.outps, 
						super.prefix + super.prefixInc, // prefix = prefix + prefixIncrement
						super.prefixInc, 
						super.pearlPrefix, super.rootPrefix, super.aliasPrefix, super.refPrefix));
			
		}
	}  // RecursiveTextPearlHandler
	

}// TextPearlHandler
