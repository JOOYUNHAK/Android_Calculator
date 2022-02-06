package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.database.DefaultDatabaseErrorHandler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

// 두번째 입력받을 때 2자리 일때만 소수점 이하 자리 0이 계속해서 생겨남
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;
    private TextView textview;
    Button []btn = new Button[19];
    StringBuffer input = new StringBuffer();
    StringBuffer second_input = new StringBuffer();
    StringBuffer zero = new StringBuffer();
    String backup = "";
    int check = 0; //// 연산부호가 눌리고 난뒤에는 계산할 숫자를 새로 받아야하므로 1로 바꿔준다.
    int again_check = 0; //가장 처음 시작할 때를 제외하곤 1이다.
    int dividing = 0;  int plusing = 0; int multifling = 0; int minusing = 0;
    int result = 0; // =으로 결과값을 받고 그대로 연산부호를 이용해 이어나갈 수 있지만 새롭게 숫자를 초기화 C버튼을 이용하지 않고 입력받아 계산하기위한 변수
    int error = 0;  int count = 0;
    DecimalFormat format = new DecimalFormat("###,###.###############");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("Caculator");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FindID(); SetListener();
        editText = findViewById(R.id.EditText_number);
        textview = findViewById(R.id.Textview_Formula);
        editText.setTextIsSelectable(false);    //커서 표시 안함
        editText.setShowSoftInputOnFocus(false);    //키보드 입력 제거

    }

    public void FindID() {
        btn[0] = findViewById(R.id.Button_zero);
        btn[1] = findViewById(R.id.Button_one);
        btn[2] = findViewById(R.id.Button_two);
        btn[3] = findViewById(R.id.Button_three);
        btn[4] = findViewById(R.id.Button_four);
        btn[5] = findViewById(R.id.Button_five);
        btn[6] = findViewById(R.id.Button_six);
        btn[7] = findViewById(R.id.Button_seven);
        btn[8] = findViewById(R.id.Button_eight);
        btn[9] = findViewById(R.id.Button_nine);
        btn[10] = findViewById(R.id.Button_reset);
        btn[11] = findViewById(R.id.Button_plustominus);
        btn[12] = findViewById(R.id.Button_backspace);
        btn[13] = findViewById(R.id.Button_divide);
        btn[14] = findViewById(R.id.Button_multiply);
        btn[15] = findViewById(R.id.Button_minus);
        btn[16] = findViewById(R.id.Button_plus);
        btn[17] = findViewById(R.id.Button_result);
        btn[18] = findViewById(R.id.Button_dot);
    }
    public void SetListener() {
            for(int i = 0; i < btn.length; i++)
                btn[i].setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.Button_zero) {
            if(result == 1 && plusing == 0 && minusing == 0 && multifling == 0 && dividing == 0 && check == 1) {
                check = 0;  result = 0; input.delete(0, input.length());
            }
            if (check == 0) {
                if (input.length() == 1) {
                    if (input.charAt(0) == '0') {
                        input.deleteCharAt(0);
                        editText.setText(input.append("0"));
                    } else {
                        editText.setText(input.append("0") );
                    }
                }
                else if(input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else {
                    if(input.toString().contains(".")) {                     //소수 이후에 0을 계속 입력하면 0이짤려서 출력되므로 0도 출력
                        String []arr = input.toString().split("\\.", -1);
                        if(arr[1].equals("")) {
                            input.append("0");
                            zero.append("0");
                            String front = String.format("%s.", format.format(Double.parseDouble(arr[0])));
                            editText.setText(front.concat(zero.toString()));
                        }
                        else {
                            for(int i = 0; i < arr[1].length(); i++) {
                                if (arr[1].charAt(i) == '0')                //실수인데 전부 0으로 채워진경우
                                    count++;
                            }
                            if(arr[1].length() == count) {
                                input.append("0");
                                zero.append("0");
                                String front = String.format("%s.", format.format(Double.parseDouble(arr[0])));
                                editText.setText(front.concat(zero.toString()));
                                count = 0;
                            }
                            else {
                                 zero.delete(0, zero.length());
                                 input.append("0");
                                 zero.append(arr[1].concat("0"));
                                 String front = String.format("%s.", format.format(Double.parseDouble(arr[0])));
                                 editText.setText(front.concat(zero.toString()));
                                 count = 0;
                            }
                        }
                    } else {
                        input.append("0");
                        editText.setText(format.format(Double.parseDouble(input.toString())));
                    }
                }
            } else {
                if (second_input.length() == 1) {
                    if (second_input.charAt(0) == '0') {
                        second_input.deleteCharAt(0);
                        editText.setText(second_input.append("0"));
                    } else {
                        editText.setText(second_input.append("0"));
                    }
                } else if (second_input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else {
                    if (second_input.toString().contains(".")) {                     //소수 이후에 0을 계속 입력하면 0이짤려서 출력되므로 0도 출력
                        String[] arr = second_input.toString().split("\\.", -1);
                        if (arr[1].equals("")) {
                            second_input.append("0");
                            zero.delete(0, zero.length()); //////////////////
                            zero.append("0");
                            String front = String.format("%s.", format.format(Double.parseDouble(arr[0])));
                            editText.setText(front.concat(zero.toString()));
                        } else {
                            for (int i = 0; i < arr[1].length(); i++) {
                                if (arr[1].charAt(i) == '0')                //실수인데 전부 0으로 채워진경우
                                    count++;
                            }
                            if (arr[1].length() == count) {
                                second_input.append("0");
                                zero.append("0");
                                String front = String.format("%s.", format.format(Double.parseDouble(arr[0])));
                                editText.setText(front.concat(zero.toString()));
                                count = 0;
                            } else {
                                zero.delete(0, zero.length());
                                second_input.append("0");
                                zero.append(arr[1].concat("0"));
                                String front = String.format("%s.", format.format(Double.parseDouble(arr[0])));
                                editText.setText(front.concat(zero.toString()));
                                count = 0;
                            }
                        }
                    } else {
                        second_input.append("0");
                        editText.setText(format.format(Double.parseDouble(second_input.toString())));
                    }
                }
            }
        }
        else if (view.getId() == R.id.Button_one) {
            //계산결과가 =버튼을 이용해 출력이되고 결과값을 가지고 계산하지않고 새롭게 계산을 시작하는 경우
            if(result == 1 && plusing == 0 && minusing == 0 && multifling == 0 && dividing == 0 && check == 1) {
                check = 0;  result = 0; input.delete(0, input.length());
            }

            if (check == 0) {
                if (input.length() == 1) {
                    if (input.charAt(0) == '0') {
                        input.deleteCharAt(0);
                        editText.setText(input.append("1"));
                    } else {
                        editText.setText(input.append("1"));
                    }
                }
                else if(input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else  {
                    input.append("1");
                    editText.setText(format.format(Double.parseDouble(input.toString())));
                    //editText.setText(input.append("1"));
                }
            } else {
                if (second_input.length() == 1) {
                    if (second_input.charAt(0) == '0') {
                        second_input.deleteCharAt(0);
                        editText.setText(second_input.append("1"));
                    } else {
                        editText.setText(second_input.append("1"));                     //////////////여기서부터
                    }
                }
                else if(second_input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else {
                        second_input.append("1");
                    editText.setText(format.format(Double.parseDouble(second_input.toString())));
                    //editText.setText(second_input.append("1"));
                }
            }
        }
        else if (view.getId() == R.id.Button_two) {
            if(result == 1 && plusing == 0 && minusing == 0 && multifling == 0 && dividing == 0 && check == 1) {
                check = 0;  result = 0; input.delete(0, input.length());
            }
            if (check == 0) {
                if (input.length() == 1) {
                    if (input.charAt(0) == '0') {
                        input.deleteCharAt(0);
                        editText.setText(input.append("2"));
                    } else {
                        editText.setText(input.append("2"));
                    }
                }
                else if(input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else {
                    input.append("2");
                    editText.setText(format.format(Double.parseDouble(input.toString())));
                    //editText.setText(input.append("2"));
                }
            } else {
                if (second_input.length() == 1) {
                    if (second_input.charAt(0) == '0') {
                        second_input.deleteCharAt(0);
                        editText.setText(second_input.append("2"));
                    } else {
                        editText.setText(second_input.append("2"));
                    }
                }
                else if(second_input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else {
                    second_input.append("2");
                    editText.setText(format.format(Double.parseDouble(second_input.toString())));
                    //editText.setText(second_input.append("2"));
                }
            }
        }
        else if (view.getId() == R.id.Button_three) {
            if(result == 1 && plusing == 0 && minusing == 0 && multifling == 0 && dividing == 0 && check == 1) {
                check = 0;  result = 0; input.delete(0, input.length());
            }
            if (check == 0) {
                if (input.length() == 1) {
                    if (input.charAt(0) == '0') {
                        input.deleteCharAt(0);
                        editText.setText(input.append("3"));
                    } else {
                        editText.setText(input.append("3"));
                    }
                }
                else if(input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else {
                    input.append("3");
                    editText.setText(format.format(Double.parseDouble(input.toString())));
                    //editText.setText(input.append("3"));
                }
            } else {
                if (second_input.length() == 1) {
                    if (second_input.charAt(0) == '0') {
                        second_input.deleteCharAt(0);
                        editText.setText(second_input.append("3"));
                    } else {
                        editText.setText(second_input.append("3"));
                    }
                }
                else if(second_input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else {
                    second_input.append("3");
                    editText.setText(format.format(Double.parseDouble(second_input.toString())));
                    //editText.setText(second_input.append("3"));
                }
            }
        }
        else if (view.getId() == R.id.Button_four) {
            if(result == 1 && plusing == 0 && minusing == 0 && multifling == 0 && dividing == 0 && check == 1) {
                check = 0;  result = 0; input.delete(0, input.length());
            }
            if (check == 0) {
                if (input.length() == 1) {
                    if (input.charAt(0) == '0') {
                        input.deleteCharAt(0);
                        editText.setText(input.append("4"));
                    } else {
                        editText.setText(input.append("4"));
                    }
                }
                else if(input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else {
                    input.append("4");
                    editText.setText(format.format(Double.parseDouble(input.toString())));
                    //editText.setText(input.append("4"));
                }
            } else {
                if (second_input.length() == 1) {
                    if (second_input.charAt(0) == '0') {
                        second_input.deleteCharAt(0);
                        editText.setText(second_input.append("4"));
                    } else {
                        editText.setText(second_input.append("4"));
                    }
                }
                else if(second_input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else {
                    second_input.append("4");
                    editText.setText(format.format(Double.parseDouble(second_input.toString())));
                    //editText.setText(second_input.append("4"));
                }
            }
        }
        else if (view.getId() == R.id.Button_five) {
            if(result == 1 && plusing == 0 && minusing == 0 && multifling == 0 && dividing == 0 && check == 1) {
                check = 0;  result = 0; input.delete(0, input.length());
            }
            if (check == 0) {
                if (input.length() == 1) {
                    if (input.charAt(0) == '0') {
                        input.deleteCharAt(0);
                        editText.setText(input.append("5"));
                    } else {
                        editText.setText(input.append("5"));
                    }
                }
                else if(input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else {
                    input.append("5");
                    editText.setText(format.format(Double.parseDouble(input.toString())));
                    //editText.setText(input.append("5"));
                }
            } else {
                if (second_input.length() == 1) {
                    if (second_input.charAt(0) == '0') {
                        second_input.deleteCharAt(0);
                        editText.setText(second_input.append("5"));
                    } else {
                        editText.setText(second_input.append("5"));
                    }
                }
                else if(second_input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else {
                    second_input.append("5");
                    editText.setText(format.format(Double.parseDouble(second_input.toString())));
                   // editText.setText(second_input.append("5"));
                }
            }
        }
        else if (view.getId() == R.id.Button_six) {
            if(result == 1 && plusing == 0 && minusing == 0 && multifling == 0 && dividing == 0 && check == 1) {
                check = 0;  result = 0; input.delete(0, input.length());
            }
            if (check == 0) {
                if (input.length() == 1) {
                    if (input.charAt(0) == '0') {
                        input.deleteCharAt(0);
                        editText.setText(input.append("6"));
                    } else {
                        editText.setText(input.append("6"));
                    }
                }
                else if(input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else {
                    input.append("6");
                    editText.setText(format.format(Double.parseDouble(input.toString())));
                    //editText.setText(input.append("6"));
                }
            } else {
                if (second_input.length() == 1) {
                    if (second_input.charAt(0) == '0') {
                        second_input.deleteCharAt(0);
                        editText.setText(second_input.append("6"));
                    } else {
                        editText.setText(second_input.append("6"));
                    }
                }
                else if(second_input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else {
                    second_input.append("6");
                    editText.setText(format.format(Double.parseDouble(second_input.toString())));
                    //editText.setText(second_input.append("6"));
                }
            }
        }
        else if (view.getId() == R.id.Button_seven) {
            if(result == 1 && plusing == 0 && minusing == 0 && multifling == 0 && dividing == 0 && check == 1) {
                check = 0;  result = 0; input.delete(0, input.length());
            }
            if (check == 0) {
                if (input.length() == 1) {
                    if (input.charAt(0) == '0') {
                        input.deleteCharAt(0);
                        editText.setText(input.append("7"));
                    } else {
                        editText.setText(input.append("7"));
                    }
                }
                else if(input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else {
                    input.append("7");
                    editText.setText(format.format(Double.parseDouble(input.toString())));
                    //editText.setText(input.append("7"));
                }
            } else {
                if (second_input.length() == 1) {
                    if (second_input.charAt(0) == '0') {
                        second_input.deleteCharAt(0);
                        editText.setText(second_input.append("7"));
                    } else {
                        editText.setText(second_input.append("7"));
                    }
                }
                else if(second_input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else {
                    second_input.append("7");
                    editText.setText(format.format(Double.parseDouble(second_input.toString())));
                    //editText.setText(second_input.append("7"));
                }
            }
        }
        else if (view.getId() == R.id.Button_eight) {
            if(result == 1 && plusing == 0 && minusing == 0 && multifling == 0 && dividing == 0 && check == 1) {
                check = 0;  result = 0; input.delete(0, input.length());
            }
            if (check == 0) {
                if (input.length() == 1) {
                    if (input.charAt(0) == '0') {
                        input.deleteCharAt(0);
                        editText.setText(input.append("8"));
                    } else {
                        editText.setText(input.append("8"));
                    }
                }
                else if(input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else {
                    input.append("8");
                    editText.setText(format.format(Double.parseDouble(input.toString())));
                    //editText.setText(input.append("8"));
                }
            } else {
                if (second_input.length() == 1) {
                    if (second_input.charAt(0) == '0') {
                        second_input.deleteCharAt(0);
                        editText.setText(second_input.append("8"));
                    } else {
                        editText.setText(second_input.append("8"));
                    }
                }
                else if(second_input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else {
                    second_input.append("8");
                    editText.setText(format.format(Double.parseDouble(second_input.toString())));
                    //editText.setText(second_input.append("8"));
                }
            }
        }
        else if (view.getId() == R.id.Button_nine) {
            if(result == 1 && plusing == 0 && minusing == 0 && multifling == 0 && dividing == 0 && check == 1) {
                check = 0;  result = 0; input.delete(0, input.length());
            }
            if (check == 0) {
                if (input.length() == 1) {
                    if (input.charAt(0) == '0') {
                        input.deleteCharAt(0);
                        editText.setText(input.append("9"));
                    } else {
                        editText.setText(input.append("9"));
                    }
                }
                else if(input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else {
                    input.append("9");
                    editText.setText(format.format(Double.parseDouble(input.toString())));
                   // editText.setText(input.append("9"));
                }
            } else {
                if (second_input.length() == 1) {
                    if (second_input.charAt(0) == '0') {
                        second_input.deleteCharAt(0);
                        editText.setText(second_input.append("9"));
                    } else {
                        editText.setText(second_input.append("9"));
                    }
                }
                else if(second_input.length() == 15)
                    Toast.makeText(MainActivity.this, "15자리까지 입력할 수 있어요!", Toast.LENGTH_SHORT).show();
                else {
                    second_input.append("9");
                    editText.setText(format.format(Double.parseDouble(second_input.toString())));
                   // editText.setText(second_input.append("9"));
                }
            }
        }
        else if (view.getId() == R.id.Button_reset) {
            editText.setText("");
            textview.setText("");
            check = 0;
            again_check = 0;
            plusing = 0;
            minusing = 0;
            multifling = 0;
            dividing = 0;
            result = 0;
            error = 0;
            count = 0;
            input.delete(0, input.length());
            second_input.delete(0, second_input.length());
            zero.delete(0, zero.length());
        }
        else if (view.getId() == R.id.Button_plustominus) {
            convert_sign(); //부호 바꾸기
        }
        else if (view.getId() == R.id.Button_backspace) {
            try {
                if (check == 0 || result == 1) {           //=버튼을 이용한 결과값을 지우고 싶을 경우
                        if (input.length() >= 16) {
                            String number2 = String.format(Locale.getDefault(), "%.8E", Double.parseDouble(input.toString()));
                            StringBuffer number3 = new StringBuffer(number2);
                            editText.setText(number3.deleteCharAt(number3.length()-1));

                            input.append(input.deleteCharAt(input.length()-1));         ///////////////지수 표현 수정요망, 지수형식에 숫자 누르면 0 오류
                        } else {
                            input.deleteCharAt(input.length() - 1);
                            if (input.toString().contains(".")) {
                                    String front = "";
                                    if (input.charAt(input.length() - 2) == '.') {
                                        String[] arr = input.toString().split("\\.", -1);
                                        if (arr[1].length() == 0) {
                                            front = String.format("%s.", format.format(Double.parseDouble(arr[0])));
                                            editText.setText(front);
                                        } else {
                                            String behind = arr[1];
                                            front = String.format("%s.", format.format(Double.parseDouble(arr[0])));
                                            editText.setText(front.concat(behind));
                                        }
                                    } else {
                                        String[] arr = input.toString().split("\\.", -1);
                                        if (arr[1].length() == 0) {
                                            front = String.format("%s.", format.format(Double.parseDouble(arr[0])));
                                            editText.setText(front);
                                        } else {
                                            String behind = arr[1];
                                            front = String.format("%s.", format.format(Double.parseDouble(arr[0])));
                                            editText.setText(front.concat(behind));
                                        }
                                    }
                            }
                            else{
                                editText.setText(format.format(Double.parseDouble(input.toString())));
                            }
                        }
                } else if ((check == 1 && again_check == 1)) {   //부호 버튼을 누르고 입력되었던 숫자 값을 지우고 싶은 경우
                    if (second_input.length() == 0) {
                        if(error == 1)
                            throw new Exception();
                        else {
                            StringBuffer backup = new StringBuffer(input);
                            String front = "";
                            second_input.append(backup.deleteCharAt(backup.length() - 1));
                            if(second_input.toString().contains(".")) {
                                if (input.charAt(input.length() - 2) == '.') {
                                    //second_input.append(backup.deleteCharAt(backup.length() - 1));
                                    String[] arr = second_input.toString().split("\\.", -1);
                                    if (arr[1].length() == 0) {
                                        front = String.format("%s.", format.format(Double.parseDouble(arr[0])));
                                        editText.setText(front);
                                    } else {
                                        String behind = arr[1];
                                        front = String.format("%s.", format.format(Double.parseDouble(arr[0])));
                                        editText.setText(front.concat(behind));
                                    }
                                    //editText.setText(String.format("%s.", format.format(Double.parseDouble(second_input.toString()))));
                                } else {
                                    String[] arr = second_input.toString().split("\\.", -1);
                                    if (arr[1].length() == 0) {
                                        editText.setText(format.format(Double.parseDouble(second_input.toString())));
                                    } else {
                                        String behind = arr[1];
                                        front = String.format("%s.", format.format(Double.parseDouble(arr[0])));
                                        editText.setText(front.concat(behind));
                                    }
                                }
                                error = 1;
                            }else
                                editText.setText(format.format(Double.parseDouble(second_input.toString())));
                        }
                    } else {
                            second_input.deleteCharAt(second_input.length() - 1);
                            if (second_input.toString().contains(".")) {
                                String front = "";
                                if (second_input.charAt(second_input.length() - 2) == '.') {
                                    String[] arr = second_input.toString().split("\\.", -1);
                                    if (arr[1].length() == 0) {
                                        front = String.format("%s.", format.format(Double.parseDouble(arr[0])));
                                        editText.setText(front);
                                    } else {
                                        String behind = arr[1];
                                        front = String.format("%s.", format.format(Double.parseDouble(arr[0])));
                                        editText.setText(front.concat(behind));
                                    }
                                } else {
                                    String[] arr = second_input.toString().split("\\.", -1);
                                    if (arr[1].length() == 0) {
                                        front = String.format("%s.", format.format(Double.parseDouble(arr[0])));
                                        editText.setText(front);
                                    } else {
                                        String behind = arr[1];
                                        front = String.format("%s.", format.format(Double.parseDouble(arr[0])));
                                        editText.setText(front.concat(behind));
                                    }
                                }
                            }
                            else{
                                editText.setText(format.format(Double.parseDouble(second_input.toString())));
                            }
                        }
                        error = 1;
                    }
            } catch (Exception e) {
                if(input.length() == 1)
                    input.delete(0, input.length());
                else if(second_input.length() == 1)
                    second_input.delete(0, second_input.length());
                editText.setText("");
                //더이상 지울 숫자가 없을 때
            }
        }
        else if (view.getId() == R.id.Button_divide) {
            if(minusing == 1 && second_input.length() == 0) {  //계산할 숫자를 입력하기전 이전에 클릭했던 연산부호 말고 다른 연산부호를 클릭했을 때
                minusing = 0;
            }
            else if(multifling == 1 && second_input.length() == 0) {
                again_check = 0;
                multifling = 0;
            }
            else if(plusing == 1 && second_input.length() == 0) {
                again_check = 0;
                plusing = 0;
            }
            error = 0;
            calculator(view.getId());
        }
        else if (view.getId() == R.id.Button_multiply) {
            if(minusing == 1 && second_input.length() == 0) { //계산할 숫자를 입력하기전 이전에 클릭했던 연산부호 말고 다른 연산부호를 클릭했을 때
                again_check = 0;
                minusing = 0;
            }
            else if(plusing == 1 && second_input.length() == 0) {
                again_check = 0;
                plusing = 0;
            }
            else if(dividing == 1 && second_input.length() == 0)  {
                again_check = 0;
                dividing = 0;
            }
            error = 0;
            calculator(view.getId());
        }
        else if (view.getId() == R.id.Button_plus) {
            if(minusing == 1 && second_input.length() == 0) { //계산할 숫자를 입력하기전 이전에 클릭했던 연산부호 말고 다른 연산부호를 클릭했을 때
                again_check = 0;
                minusing = 0;
            }
            else if(multifling == 1 && second_input.length() == 0)  {
                again_check = 0;
                multifling = 0;
            }
            else if(dividing == 1 && second_input.length() == 0) {
                again_check = 0;
                dividing = 0;
            }
            error = 0;
            calculator(view.getId());
        }
        else if (view.getId() == R.id.Button_minus) {
            if(plusing == 1 && second_input.length() == 0) { //계산할 숫자를 입력하기전 이전에 클릭했던 연산부호 말고 다른 연산부호를 클릭했을 때
                again_check = 0;
                plusing = 0;
            }
            else if(multifling == 1 && second_input.length() == 0) {
                again_check = 0;
                multifling = 0;
            }
            else if(dividing == 1 && second_input.length() == 0) {
                again_check = 0;
                dividing = 0;
            }
            error = 0;
            calculator(view.getId());
        }
        else if (view.getId() == R.id.Button_result) {
            result = 1; error = 0; check = 0;
            try {
                if(second_input.length() == 0) { //두번째 숫자가 들어오지 않은 상황에서 =버튼을 눌렀다가 다른 버튼을 눌렀을 경우
                    if(plusing ==  1)
                        plusing = 0;
                    else if(minusing == 1)
                        minusing = 0;
                    else if(multifling == 1)
                        multifling = 0;
                    else if(dividing == 1)
                        dividing = 0;
                    else if(result == 1) {
                            check = 0;
                            input.delete(0, input.length());
                            input.append("0");
                            result = 0;
                        }
                    }
                else {
                    if (plusing == 1) {
                        plus(Double.parseDouble(input.toString()), Double.parseDouble(second_input.toString()));
                    } else if (minusing == 1) {
                        minus(Double.parseDouble(input.toString()), Double.parseDouble(second_input.toString()));
                    } else if (multifling == 1) {
                        multifly(Double.parseDouble(input.toString()), Double.parseDouble(second_input.toString()));
                    } else if (dividing == 1) {
                        divide(Double.parseDouble(input.toString()), Double.parseDouble(second_input.toString()));
                    }
                }
            }
            catch(Exception e) {
                //결과가 나왔는데도 계속해서 누를 때, 아무 식이 없는데 누를 때
            }
            again_check = 0;


        }
        else if (view.getId() == R.id.Button_dot) {
            try {
                if (check == 0) {
                    if (input.toString().contains("."))
                        throw new Exception();
                    else {
                        if(input.length() == 0) {           //맨처음으로 .을 입력할경우 0.으로 실수로 바꿔준다
                            editText.setText(input.append("0"));    ////////////////////////////////
                            editText.setText(input.append("."));
                        }
                        else {
                            if (input.toString().contains("."))
                                throw new Exception();
                            input.append(".");
                            editText.setText(String.format("%s.", format.format(Double.parseDouble(input.toString()))));
                            //input.deleteCharAt()

                        }
                    }
                }
                else if(check == 1){
                    if (second_input.toString().contains("."))
                        throw new Exception();
                    else {
                        if(second_input.length() == 0) {           //맨처음으로 .을 입력할경우 0.으로 실수로 바꿔준다
                            editText.setText(second_input.append("0"));
                            editText.setText(second_input.append("."));
                        }
                        else {
                            if (second_input.toString().contains("."))
                                throw new Exception();
                            second_input.append(".");
                            editText.setText(String.format("%s.", format.format(Double.parseDouble(second_input.toString()))));
                            //editText.setText(second_input.append("."));
                        }
                    }
                }
            } catch (Exception e) {                 //.이 있는데 또 추가하려고 할 경우

            }
        }
    }
    public void convert_sign() {
        String string_input = input.toString();
        String second_string_input = second_input.toString();
        try {
            if(input.length() == 0)
                Toast.makeText(MainActivity.this, "숫자를 입력하고 부호를 바꿔주세요", Toast.LENGTH_SHORT ).show();
                if(input.length() != 0 && second_input.length() == 0) {   ///결과값이 출력됐을 때 그 결과값의 부호를 바꾸고 싶은경우
                        if(string_input.contains(".")) {                         //실수일 때
                            Double tominus = Double.parseDouble(input.toString()) * (-1);
                            input.delete(0, input.length());
                            input.append(tominus.toString());
                            String []arr = input.toString().split("\\.");
                            String behind = ".".concat(arr[1]);
                            editText.setText(format.format(Double.parseDouble(arr[0])).concat(behind));      /////////부호
                        }
                   else if(string_input.contains(".")) {                         //실수일 때
                       Double tominus = Double.parseDouble(input.toString()) * (-1);
                       input.delete(0, input.length());
                       input.append(tominus.toString());
                       String []arr = input.toString().split("\\.");
                       String behind = ".".concat(arr[1]);
                       editText.setText(format.format(Double.parseDouble(arr[0])).concat(behind));      /////////부호
                   }
                    else {
                        Long tominus = Long.parseLong(input.toString()) * (-1);  /////정수일 때
                        input.delete(0, input.length());
                        input.append(tominus.toString());
                        editText.setText(format.format(Long.parseLong(input.toString())));
                        //editText.setText(input.append(tominus.toString()));
                    } }
                else {
                    if(second_string_input.contains(".")) {  ////////실수일 때
                        Double tominus = Double.parseDouble(second_input.toString()) * (-1);
                        second_input.delete(0, second_input.length());
                        editText.setText(second_input.append(tominus.toString()));
                        String []arr = input.toString().split("\\.");
                        String behind = ".".concat(arr[1]);
                        editText.setText(format.format(Double.parseDouble(arr[0])).concat(behind));
                    }
                    else {
                        Long tominus = Long.parseLong(second_input.toString()) * (-1);  /////정수일 때
                        second_input.delete(0, second_input.length());
                        second_input.append(tominus.toString());
                        editText.setText(format.format(Long.parseLong(second_input.toString())));
                        //editText.setText(second_input.append(tominus.toString()));
                        }
                }

        }
        catch(Exception e) {
            ///아무것도 입력안되어있을 때 부호를 바꾸려하면 버튼만 클릭되게 구현
        }
    }
    public void calculator(int id) {
        double num1; double num2;
        check = 1;
        if(again_check == 1)
            editText.setText(input.toString());     // =을 이용하지않고 연산부호를 이용해서 계속해서 연산을 수행하는 경우 결과값이 출력
        try {
            if(check == 1 && again_check == 1) {    //연산할 숫자와 당하는 숫자 모두 입력이 된 경우
                if(input.length() == 0) {
                    if (second_input.length() != 0)
                        editText.setText(second_input.toString());
                    check = 0;
                }
                if(second_input.length() == 0) {
                    num1 = Double.parseDouble(input.toString());    //버튼을 계속해서 누를경우
                    if(id == R.id.Button_divide || id == R.id.Button_multiply)
                        num2 = 1.0; //나누기나 곱하기 버튼을 계속해서 누르면 원래 있던 값이 계속해서 나와야하므로 1로 나눠준다.
                    else
                        num2 = 0.0;
                } else {
                    num1 = Double.parseDouble(input.toString());
                    num2 = Double.parseDouble(second_input.toString());
                }
                if(id == R.id.Button_divide) {
                    divide(num1, num2);
                    dividing = 1;                   //계속해서 연산버튼을 누르면서 계산하기 때문에 앞선 연산이 먼저 수행이 되게 처리
                }
                else if(id == R.id.Button_plus) {
                    plus(num1, num2);
                    plusing = 1;                   //계속해서 연산버튼을 누르면서 계산하기 때문에 앞선 연산이 먼저 수행이 되게 처리
                }
                else if(id == R.id.Button_minus) {
                    minus(num1, num2);
                    minusing = 1;                   //계속해서 연산버튼을 누르면서 계산하기 때문에 앞선 연산이 먼저 수행이 되게 처리
                }
                else if(id == R.id.Button_multiply) {
                    multifly(num1, num2);
                    multifling = 1;                   //계속해서 연산버튼을 누르면서 계산하기 때문에 앞선 연산이 먼저 수행이 되게 처리
                }
            }
            else if(check == 1 && again_check == 0) { //맨처음 시작할 때 입력숫자 한개와 연산부호 하나만 입력되었을경우
                if(id == R.id.Button_divide)
                    dividing = 1;
                else if(id == R.id.Button_plus)
                    plusing = 1;
                else if(id == R.id.Button_minus)
                    minusing = 1;
                else if(id == R.id.Button_multiply)
                    multifling = 1;
                again_check = 1;           //두번째 숫자가 아직 입력이 안된경우
            }
        }
        catch(Exception e) {

        }
    }
    public void divide(double a, double b) {

        if(plusing == 1) {  //만약 해당 연산부호를 눌렀을 때 앞에 연산이 아직 수행되지 않고 남아있다면 먼저 수행한다.
            calculator(R.id.Button_plus);
            plusing = 0;
        } else if (minusing == 1) {
            calculator(R.id.Button_minus);
            minusing = 0;
        } else if (multifling == 1) {
            calculator(R.id.Button_multiply);
            multifling = 0;
        } else {
            try {
                if (b == 0)
                    throw new Exception(); //0으로 나눴을 때 예외
                    DecimalFormat format = new DecimalFormat("###,###.########");
                    String result = format.format(a / b);
                    String result2 = result.replaceAll("\\,", ""); // 반점을 없앤 숫자의 개수를 파악하기 위해
                    input.delete(0, input.length());
                    input.append(result2);
                    second_input.delete(0, second_input.length());
                    if (again_check == 1) {
                        if (result2.length() >= 16) {
                            editText.setText(String.format(Locale.getDefault(), "%.8E", Double.parseDouble(result2)));
                        } else
                            editText.setText(result.toString());
                    }

            } catch (Exception e) {      //0으로 나눴을 때 예외처리
                Toast.makeText(MainActivity.this, "0으로 나눌 수 없습니다", Toast.LENGTH_SHORT).show();
                input.delete(0, input.length());
                second_input.delete(0, second_input.length());
                check = 0;  result = 1;
                editText.setText("");
            }
            dividing = 0;
        }
    }
    public void plus(double a, double b) {
        if(dividing == 1) {  //만약 해당 연산부호를 눌렀을 때 앞에 연산이 아직 수행되지 않고 남아있다면 먼저 수행한다.
            calculator(R.id.Button_divide);
            dividing = 0;
        } else if(minusing == 1) {
            calculator(R.id.Button_minus);
            minusing = 0;
        } else if(multifling == 1) {
            calculator(R.id.Button_multiply);
            multifling = 0;
        } else {
            DecimalFormat format = new DecimalFormat("###,###.########");
            if(isZeroDouble(Double.toString(a))) {
                if (isZeroDouble(Double.toString(b)))
                    textview.setText(Long.toString((long) a) + "+" + Long.toString((long) b));  // 1/16일 수정, long long 범위 확인)
                else
                    textview.setText(Long.toString((long) a) + "+" + Double.toString(b));
            } else {
                if (isZeroDouble(Double.toString(b)))
                    textview.setText(Double.toString(a) + "+" + Long.toString((long) b));
                else
                    textview.setText(Double.toString(a) + "+" + Double.toString(b));
            }
            String result = format.format(a + b);
            String result2 = result.replaceAll("\\,", ""); // 반점을 없앤 숫자의 개수를 파악하기 위해
            input.delete(0, input.length());
            input.append(result2);
            second_input.delete(0, second_input.length());

            if (again_check == 1) {
                if(result2.length() >= 16) {
                    editText.setText(String.format(Locale.getDefault(), "%.8E", Double.parseDouble(result2)));
                }
                else
                    editText.setText(result.toString());
            }
            second_input.delete(0, second_input.length());
            plusing = 0;

        }
    }
    public void minus(double a, double b) {
        if(dividing == 1) {  //만약 해당 연산부호를 눌렀을 때 앞에 연산이 아직 수행되지 않고 남아있다면 먼저 수행한다.
            calculator(R.id.Button_divide);
            dividing = 0;
        } else if(plusing == 1) {
            calculator(R.id.Button_plus);
            plusing = 0;
        } else if(multifling == 1) {
            calculator(R.id.Button_multiply);
            multifling = 0;
        } else {
            DecimalFormat format = new DecimalFormat("###,###.########");
            String result = format.format(a - b);
            String result2 = result.replaceAll("\\,", ""); // 반점을 없앤 숫자의 개수를 파악하기 위해
            input.delete(0, input.length());
            input.append(result2);
            second_input.delete(0, second_input.length());
            if (again_check == 1) {
                if(result2.length() >= 16) {
                    editText.setText(String.format(Locale.getDefault(),"%.8E", Double.parseDouble(result2)));
                }
                else
                    editText.setText(result.toString());
            }
        }
    }
    public void multifly(double a, double b) {
        if (dividing == 1) {  //만약 해당 연산부호를 눌렀을 때 앞에 연산이 아직 수행되지 않고 남아있다면 먼저 수행한다.
            calculator(R.id.Button_divide);
            dividing = 0;
        } else if (plusing == 1) {
            calculator(R.id.Button_plus);
            plusing = 0;
        } else if (minusing == 1) {
            calculator(R.id.Button_minus);
            minusing = 0;
        } else {
            DecimalFormat format = new DecimalFormat("###,###.########");
            String result = format.format(a * b);
            String result2 = result.replaceAll("\\,", ""); // 반점을 없앤 숫자의 개수를 파악하기 위해
            input.delete(0, input.length());
            input.append(result2);
            second_input.delete(0, second_input.length());
            if (again_check == 1) {
                if(result2.length() >= 16) {
                    editText.setText(String.format(Locale.getDefault(),"%.8E", Double.parseDouble(result2)));
                }
                else
                    editText.setText(result.toString());
            }
            multifling = 0;
        }
    }


    public boolean isZeroDouble(String a) {
        int count = 0;  String []arr;
        if(a.contains("."))
            arr = a.split("\\.");
        else
            return false;

        for(int i = 0; i < arr[1].length(); i++) {
            if(arr[1].charAt(i) == '0')
                count++;
        }
        if(count == arr[1].length())
            return true;
        else
            return false;
    }

}
