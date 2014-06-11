#!/usr/local/bin/perl
#
# 
#

	undef $/;
	my $source_dir = $ARGV[0];
	my $source_path = $source_dir."WsgRegGradableItem_Gen.java";
	my $target_path = $source_dir."WsgRegGradableItem_Gen.cs";
	open(INFI, "<", $source_path) || die("can't open ".$source_path.": $!");
	open(OUTF, ">", $target_path) || die("can't open ".$target_path.": $!");
	$data = <INFI>;

	my @using = qw(System System.Collections System.Collections.Generic System.Linq System.Text);
	my $using;
	foreach (@using) {
		$using = "${using}using $_;\n";
	}
	my @modif = qw(private protected public static void);
	my $modif = join("|", @modif);

	my @rettype = qw(void);
	my $rettype = join("|", @rettype);
	
	convertWsgRegulation(); 
	$data = "${data}\n}";
	print OUTF $data;
	
	
	sub convertWsgRegulation {
		convertPackageAndImport();
		removeUnnecessaryRegulations();
		convertClassDeclaration();
		convertLinkedHashMapDeclaration();
		convertMethodDeclaration();
		convertLHMPutCall();
		convertALNewCall();
		convertALAddCall();
	}
	
	sub convertPackageAndImport {
		#$data =~ s/^(package)(.*)(;)$/${using}\nnamespace $2 {/gmx;
		$data =~ s/^(package)\s*(wsgen.*)(;)$/${using}\nnamespace ATCGen {/gmx;
		$data =~ s/^(import)(.*)$//gxm;
	}
	sub convertClassDeclaration {
		#$data =~ s/^(\s*)((($modif)\s+)*)\s*(class)\s*(\S+)\s*{\s*$/$1$2${5} $6 {/gxm;
		$data =~ s/^(\s*)((($modif)\s+)*)\s*(class)\s*(\S+)\s*\n\s*extends\s*(\S+)\n\s*{\s*$/$1$2${5} $6: $7 {/gxm;
	}
	sub removeUnnecessaryRegulations {
		#$data =~ s/^(\s*)((($modif)\s+)*)(fillExcludedBbToucherMethodNamesMap)\(\)\s*{.*Override//gxm;
		#$data =~ s/^(\s*)((($modif)\s+)*)s*(fillExcludedBbToucherMethodNamesMap)\(\)//gxm;
		#$data =~ s/^\s*\@Override$/affdasaf/gxm;
		#$data =~ s/^\s*\@Override\n/affdasaf/gxm;
		#$data =~ s/^\s*\@Override\n.*fillExcludedBbToucherMethodNamesMap/affdasaf/gxm;
		#$data =~ s/^\s*\@Override.*fillExcludedBbToucherMethodNamesMap/affdasaf/gxms;
		$data =~ s/^\s*\@Override\n.*(fillExcludedBbToucherMethodNamesMap)/$1/gxm;
		$data =~ s/^fillExcludedBbToucherMethodNamesMap.*(^\s*\@Override.*fillBBToucher2wsFieldMaps)/$1/gxms;
		#$data =~ s/^\s*\@Override\n.*fillExcludedBbToucherMethodNamesMap.*(^\s*\@Override.*fillBBToucher2wsFieldMaps)/$1/gxms;
		$data =~ s/^\s*\@Override\n.*(fillExcludedApiMethodNamesMap)/$1/gxm;
		$data =~ s/^fillExcludedApiMethodNamesMap.*(^\s*\@Override.*fillApiRegulationList)/$1/gxms;
		
	}
	
	
	sub convertLinkedHashMapDeclaration {
		#$data =~ s/^(\s*)((($modif)\s+)*)\s*LinkedHashMap\s*(\S+);$/$1$2Hashtable $5;/gxm;
		$data =~ s/^(\s*)((($modif)\s+)*)\s*LinkedHashMap(<\S+\s*,\s*\S+>)\s*([^\s\(\)]+);$/$1$2Dictionary$5 $6;/gxm;
		$data =~ s/^(\s*)((($modif)\s+)*)\s*ArrayList(<\S+\s*,\s*\S+>)\s*([^\s\(\)]+);$/$1$2List$5 $6;/gxm;
		#$data =~ s/^(\s*)((($modif)\s+)*)\s*LinkedHashMap\s*(\S+)\s*=\s*new\s*LinkedHashMap\(\);$/$1$2Hashtable $5 = new Hashtable();/gxm;
		$data =~ s/^(\s*)((($modif)\s+)*)\s*LinkedHashMap(<\S+\s*,\s*\S+>)\s*([^\s\(\)]+)\s*=\s*new\s*LinkedHashMap(<\S+\s*,\s*\S+>)\(\);$/$1$2Dictionary$5 $6 = new Dictionary$7();/gxm;
		$data =~ s/^(\s*)((($modif)\s+)*)\s*ArrayList(<\S+>)\s*([^\s\(\)]+)\s*=\s*new\s*ArrayList(<\S+>)\(\);$/$1$2List$5 $6 = new List$7();/gxm;
	}
	sub convertMethodDeclaration {
		#$data =~ s/^(\s*)((($modif)\s+)*)s*(\S+)\(\)\s*{$/$1$2$5() {/gxm;
		$data =~ s/^(\s*)\@Override\n\s*((($modif)\s+)*)s*(\S+)\(\)\s*{$/$1override $2$5() {/gxm;
	}
	sub convertLHMPutCall {
		$data =~ s/^(\s*)(\S+)\.put\((.*)(\);)$/$1$2.Add($3$4/gxm;
	}

	sub convertALAddCall {
		$data =~ s/^(\s*)(\S+)\.add\((.*)(\);)$/$1$2.Add($3$4/gxm;
	}
	sub convertALNewCall {
		$data =~ s/^(\s*)([^\s\(\)]+)\s*=\s*new\s*ArrayList(<\S+>)\(\);$/$1$2 = new List$3();/gxm;
	}	
	
	
	


