int func1(int in1) {

  int i;
  int val0, val1;
  int result;

  if(in1 == 0) {
    return 0;
  }
  else if(in1 == 1 || in1 == 2) {
    return 1;
  }
  else {
    val0 = 1;
    val1 = 1;
    result = 0;
    for(i=0; i<in1-2; i++) {
      result = val1 + val0;
      val0 = val1;
      val1 = result;
    }
    return result;
  }
}
