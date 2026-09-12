package tp.xt;
import java.util.*;
class ReplaceEntity
{
	Hashtable entityList;
	ReplaceEntity()
	{
		entityList=new Hashtable();
		
		(entityList).put(new String("&times;"), new String("x"));
		(entityList).put(new String("&eacute;"), new String("e"));
		(entityList).put(new String("&aacute;"), new String("a"));
		(entityList).put(new String("&iacute;"), new String("i"));
		(entityList).put(new String("&auml;"), new String("a"));
		(entityList).put(new String("&ouml;"), new String("o"));
		(entityList).put(new String("&Ouml;"), new String("O"));
		(entityList).put(new String("&Auml;"), new String("A"));
		(entityList).put(new String("&uuml;"), new String("u"));
		(entityList).put(new String("&Uuml;"), new String("U"));
		(entityList).put(new String("&plus;"), new String("+"));
		(entityList).put(new String("&minus;"), new String("-"));
		(entityList).put(new String("&tilde;"), new String("~"));
		(entityList).put(new String("&ast;"), new String("*"));
		(entityList).put(new String("&openstar;"), new String("*"));
		(entityList).put(new String("&gt;"), new String(">"));
		(entityList).put(new String("&lt;"), new String("<"));
		(entityList).put(new String("&rsquo;"), new String("\""));
		(entityList).put(new String("&lsquo;"), new String("\""));
		
		(entityList).put(new String("&alpha;"), new String("alpha"));
		(entityList).put(new String("&beta;"), 	new String("beta"));
		(entityList).put(new String("&chi;"), 	new String("chi"));
		(entityList).put(new String("&Delta;"), new String("Delta"));
		(entityList).put(new String("&delta;"),	new String("delta"));
		(entityList).put(new String("&epsi;"), 	new String("epsi"));
		(entityList).put(new String("&eta;"), 	new String("&eta;"));
		(entityList).put(new String("&Gamma;"), new String("Gamma"));
		(entityList).put(new String("&gamma;"), new String("gamma"));
		(entityList).put(new String("&iota;"), 	new String("iota"));
		(entityList).put(new String("&kappa;"), new String("kappa"));
		(entityList).put(new String("&lambda;"),new String("lambda"));
		(entityList).put(new String("&Lambda;"),new String("Lambda"));
		(entityList).put(new String("&mu;"), 	new String("mu"));
		(entityList).put(new String("&Omega;"), new String("Omega"));
		(entityList).put(new String("&omicr;"), new String("omicr"));
		(entityList).put(new String("&Phi;"), 	new String("Phi"));
		(entityList).put(new String("&Pi;"), 	new String("Pi"));
		(entityList).put(new String("&psi;"), 	new String("psi"));
		(entityList).put(new String("&Psi;"), 	new String("Psi"));
		(entityList).put(new String("&rho;"), 	new String("rho"));
		(entityList).put(new String("&Sigma;"), new String("Sigma"));
		(entityList).put(new String("&tau;"), 	new String("tau"));
		(entityList).put(new String("&theta;"), new String("theta"));
		(entityList).put(new String("&Theta;"), new String("Theta"));
		(entityList).put(new String("&Xi;"), 	new String("Xi"));
		(entityList).put(new String("&xi;"), 	new String("xi"));
		(entityList).put(new String("&zeta;"), 	new String("zeta"));
		(entityList).put(new String("&z.betav;"),new String("z.betav"));
		(entityList).put(new String("&kappav;"),new String("kappav"));
		(entityList).put(new String("&rhov;"), 	new String("rhov"));
		(entityList).put(new String("&phi;"), 	new String("phi"));
		(entityList).put(new String("&thetav;"),new String("thetav"));
		(entityList).put(new String("&pi;"), 	new String("pi"));
		(entityList).put(new String("&sigma;"), new String("sigma"));
		(entityList).put(new String("&Sigmav;"),new String("Sigmav"));
		(entityList).put(new String("&iiota;"), new String("iiota"));
		(entityList).put(new String("&piv;"), 	new String("piv"));
		(entityList).put(new String("&phiv;"), 	new String("phiv"));
		(entityList).put(new String("&part;"), 	new String("part"));
		(entityList).put(new String("&epsiv;"), new String("epsiv"));
		
		
		(entityList).put(new String("&iexcl;"), new String((char)(int)161+""));
		(entityList).put(new String("&pound;"), new String((char)(int)163+""));
		(entityList).put(new String("&yen;"), 	new String((char)(int)165+""));
		(entityList).put(new String("&brvbar"), new String((char)(int)166+""));
		(entityList).put(new String("&sect;"), 	new String((char)(int)167+""));
		(entityList).put(new String("&copy;"), 	new String((char)(int)169+""));
		(entityList).put(new String("&z.ausco"),new String((char)(int)170+""));
		(entityList).put(new String("&laquo"), 	new String((char)(int)171+""));
		(entityList).put(new String("&urcorn;"),new String((char)(int)172+""));
		(entityList).put(new String("&deg;"), 	new String((char)(int)176+""));
		(entityList).put(new String("&mu;"), 	new String((char)(int)181+""));
		(entityList).put(new String("&para;"), 	new String((char)(int)182+""));
		(entityList).put(new String("&middot;"),new String((char)(int)183+""));
		(entityList).put(new String("&z.ousco;"),new String((char)(int)186+""));
		(entityList).put(new String("&raquo;"), new String((char)(int)187+""));
		(entityList).put(new String("&iquest;"),new String((char)(int)191+""))	;
		(entityList).put(new String("A&grave;"),new String((char)(int)192+""));
		(entityList).put(new String("A&acute;"),new String((char)(int)193+""));
		(entityList).put(new String("A&circ;"), new String((char)(int)194+""));
		(entityList).put(new String("A&tilde;"),new String((char)(int)195+""));
		
		(entityList).put(new String("A&uml;"), 	new String((char)(int)196+""));
		(entityList).put(new String("A&ring;"), new String((char)(int)197+""));
		(entityList).put(new String("&AElig;"), new String((char)(int)198+""));
		(entityList).put(new String("C&cedil;"),new String((char)(int)199+""));
		(entityList).put(new String("E&grave;"),new String((char)(int)200+""));
		(entityList).put(new String("E&acute;"),new String((char)(int)201+""));
		(entityList).put(new String("E&circ;"), new String((char)(int)202+""));
		(entityList).put(new String("E&uml;"), 	new String((char)(int)203+""));
		(entityList).put(new String("I&grave;"),new String((char)(int)204+""));
		(entityList).put(new String("I&acute;"),new String((char)(int)205+""));
		(entityList).put(new String("I&circ;"), new String((char)(int)206+""));
		(entityList).put(new String("I&uml;"), 	new String((char)(int)207+""));
		(entityList).put(new String("&ETH;"), 	new String((char)(int)208+""));
		(entityList).put(new String("D&z.xl;"), new String((char)(int)208+""));
		(entityList).put(new String("N&tilde;"),new String((char)(int)209+""));
		(entityList).put(new String("O&grave;"),new String((char)(int)210+""));
		(entityList).put(new String("O&acute;"),new String((char)(int)211+""));
		(entityList).put(new String("O&circ;"), new String((char)(int)212+""));
		(entityList).put(new String("O&tilde;"),new String((char)(int)213+""));
		(entityList).put(new String("O&uml;"), 	new String((char)(int)214+""));
		(entityList).put(new String("&z.Times"),new String((char)(int)215+""));
		(entityList).put(new String("O&slash;"),new String((char)(int)216+""));
		(entityList).put(new String("U&grave;"),new String((char)(int)217+""));
		(entityList).put(new String("U&acute;"),new String((char)(int)218+""));
		(entityList).put(new String("U&circ;"), new String((char)(int)219+""));
		(entityList).put(new String("U&uml;"), 	new String((char)(int)220+""));
		(entityList).put(new String("Y&acute;"),new String((char)(int)221+""));
		(entityList).put(new String("&THRON;"), new String((char)(int)222+""));
		(entityList).put(new String("&szlig;"), new String((char)(int)223+""));
		(entityList).put(new String("a&grave;"),new String((char)(int)224+""));
		(entityList).put(new String("a&acute;"),new String((char)(int)225+""));
		(entityList).put(new String("a&circ;"), new String((char)(int)226+""));
		(entityList).put(new String("a&tilde;"),new String((char)(int)227+""));
		(entityList).put(new String("a&uml;"), 	new String((char)(int)228+""));
		(entityList).put(new String("o&ring;"), new String((char)(int)229+""));
		(entityList).put(new String("&aelig;"), new String((char)(int)230+""));
		(entityList).put(new String("c&cedil;"),new String((char)(int)231+""));
		(entityList).put(new String("e&grave;"),new String((char)(int)232+""));
		(entityList).put(new String("e&acute;"),new String((char)(int)233+""));
		(entityList).put(new String("e&circ;"), new String((char)(int)234+""));
		(entityList).put(new String("e&uml;"), 	new String((char)(int)235+""));
		(entityList).put(new String("i&grave;"),new String((char)(int)236+""));
		(entityList).put(new String("i&acute;"),new String((char)(int)237+""));
		(entityList).put(new String("i&circ;"), new String((char)(int)238+""));
		(entityList).put(new String("i&uml;"), 	new String((char)(int)239+""));
		(entityList).put(new String("&eth;"), 	new String((char)(int)240+""));	
		(entityList).put(new String("n&tilde;"),new String((char)(int)241+""));
		(entityList).put(new String("o&grave;"),new String((char)(int)242+""));
		(entityList).put(new String("o&acute;"),new String((char)(int)243+""));
		(entityList).put(new String("o&circ;"), new String((char)(int)244+""));
		(entityList).put(new String("o&tilde;"),new String((char)(int)245+""));
		(entityList).put(new String("o&uml;"), 	new String((char)(int)246+""));
		(entityList).put(new String("&divide;"),new String((char)(int)247+""));
		(entityList).put(new String("o&slash;"),new String((char)(int)248+""));
		(entityList).put(new String("u&grave;"),new String((char)(int)249+""));
		(entityList).put(new String("u&acute;"),new String((char)(int)250+""));
		(entityList).put(new String("u&circ;"),	new String((char)(int)251+""));
		(entityList).put(new String("u&uml;"),	new String((char)(int)252+""));
		(entityList).put(new String("y&acute;"),new String((char)(int)253+""));
		(entityList).put(new String("&thron;"),	new String((char)(int)254+""));
		(entityList).put(new String("y&uml;"),	new String((char)(int)255+""));
		(entityList).put(new String("&quot;"),	new String((char)(int)34+""));
		(entityList).put(new String("&amp;"),	new String((char)(int)38+""));
		(entityList).put(new String("&lt;"),	new String((char)(int)60+""));
		(entityList).put(new String("&ast;"),	new String((char)(int)42+""));
		(entityList).put(new String("&z.drule;"),new String((char)(int)92+""));
		(entityList).put(new String("&z.urule;"),new String((char)(int)47+""));
		(entityList).put(new String("&sim;"),	new String((char)(int)126+""));
		(entityList).put(new String("&prime;"),	new String((char)(int)180+""));
		(entityList).put(new String("&Colon;"),	new String("::"));
		(entityList).put(new String("&lsquo;"),	new String((char)(int)39+""));
		(entityList).put(new String("&rsquo;"),	new String((char)(int)39+""));
		(entityList).put(new String("&ldquo;"),	new String((char)(int)34+""));
		(entityList).put(new String("&rdquo;"),	new String((char)(int)34+""));
		(entityList).put(new String("&z.sbs;"),	new String((char)(int)92+""));
		(entityList).put(new String("&z.reapos;"),new String((char)(int)38+""));
		(entityList).put(new String("&colone;"),new String(":="));
		(entityList).put(new String("&mdash;"),	new String("-"));
		(entityList).put(new String("&ndash;"),	new String("-"));
		(entityList).put(new String("&hyphen;"),new String("-"));
		(entityList).put(new String("&minus;"),	new String("-"));
		(entityList).put(new String("&bprime;"),new String((char)(int)96+""));		
		(entityList).put(new String("&z.bar;"), new String((char)(int)38+""));
	}		
	public String replace(String lineStr)
	{
		int len=lineStr.length();
		boolean find=false;
		String filterStr="";
		for(int i=0; i<len; i++)
		{
			char ch=lineStr.charAt(i);
			if (ch=='&')
			{
				String ent="";
				find=false;
				do
				{
					ent+=ch;
					i++;
					ch=lineStr.charAt(i);
				}while((ch!=';')&&(i!=len)&&(ch!='&'));
				if (ch==';')
				{
					find=true;
					ent+=ch;
				}
				else if(ch=='&')
				{
					i--;
				}
				
				if (find)
				{
					ent=(String)entityList.get(ent);
					if(ent!=null)
						filterStr+=ent;
				}
				else
				{
					filterStr+=ent;
				}
			}			
			else
			{
				filterStr+=ch;
			}
		}
		return filterStr;
	}
//	public static void main(String args[])
//	{
//		Test t=new Test();
//		System.out.println(t.replEnt("&alpha;&Introdu&alpha;&test;;"));
//	}
}
	
