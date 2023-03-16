# SimpleEditor

---------
# categories issues:

## 1.Product operation:<p> Requirements that directly affect the daily operation of the software :
Correctness, reliability, efficiency, integrity, and usability</p>


## 2.Product revision: <p>Requirements that affect the complete range of software maintenance activities
 Maintainability;  flexibility;  testability .</P>
 <p>1. error(181,8) && error(283,9) Conditional expressions are always tru : 
 Conditional expressions which are always true or false can lead to dead code.It is lead to major impairments of system mantinability .</p>
  <p>2. error(126,2) block of comminted-out line of code should be removed: Programmers should not comment out code as it bloats programs and reduces readability and mantinability.</p>
  <p>3. error(199,16) Incorrect exception handling: Using generic exceptions prevents calling methods from handling true, system-generated exceptions differently than application-generated errors.so It is lead to major impairments of system mantinability.</p>
  <p>4. error(42) copy,posts,cut,move should be static final : Public class variable fields do not respect the encapsulation principle and has main disadvantage ,Member values are subject to change from anywhere in the code and may not meet the programmer’s assumptions. so It is lead to major impairments of system Reliability.</p>
<p>5. error(42) Multiple variables should not be declared on the same line: Declaring multiple variables on one line is difficult to read.so It is lead to reduces system readability and mantinability.</p>
<p>6. error(166,5) && error(196,4) replace system.out by logger :If a program directly writes to the standard outputs, there is absolutely no way to comply with those requirements. so It is lead to reduces system Testability.</p>
 <p>5. error(228,60) declare constant inested of String duplicated: Duplicated string literals make the process of refactoring error-prone, since you must be sure to update all occurrences.so It is lead to reduces system flexibility.</P>
 
 
## 3.Product transition :<p> adaptation of software to other environments and its interaction with other software systems
Portability;  Reusability;  Interoperability</p>


## false Negative issues:
 <p>1. in FindDialog class (46) : setLayout(new GridLayout(3, 1)); must 3 and 1 be replaced by magic numbers , It gives the number meaning so anyone can read it and know what the number is used for.</p>
