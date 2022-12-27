import { Container } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useState, useContext } from "react";
import Header from "../components/Header";
import { FaRunning } from 'react-icons/fa'
import { Context } from "../App";
import axios from "axios";
import Password from "../components/Password";
import Input from "../components/Input";
import MuiButton from "../components/MuiButton";
import Calendar from "../components/Calendar";
import SelectBox from "../components/SelectBox";
import { Box } from "@mui/material";

function Login() {
    const setGlobal = useContext(Context).setGlobal;
    const navigate = useNavigate();

    // const clickSignUp = () => {
    //     // Empty Check
    //     for (const item of Object.keys(account)) {
    //         if (account[item][0] === '') {
    //             alert(item + "을(를) 입력해주세요.");
    //             setAccount({...account, [item]:['', 'N', '2px solid red']});
    //             return false;
    //         }
    //     }

    //     // Password Check
    //     if (account.password[0] !== account.confirm[0]) {
    //         alert('비밀번호가 일치하지 않습니다.');
    //         setAccount({...account, ['confirm']:['', 'N', '2px solid red']});
    //         return false;
    //     }

    //     axios.post("/auth/signup", {
    //         'id': account.id[0],
    //         'password': account.password[0],
    //         'name': account.name[0],
    //         'phone': account.phone[0],
    //         'gender': account.gender[0],
    //         'birth': account.birth[0]
    //     })
    //     .then(response => {
    //         console.log(response);
    //         if (response.data.result === "success") {
    //             alert('회원가입이 완료되었습니다.');
    //             onChnage('', 'reset');
    //             setSignup(false);
    //         } else throw new Error("회원가입 실패.");
    //     })
    //     .catch(error => {
    //         console.log(error)
    //         alert('서버에서 오류가 발생했습니다.');
    //     })
    // };

    // // Login
    // const clickLogin = () => {
    //     setGlobal('onLoad', true);
    //     if (account.id[0] === '') {
    //         alert("E-mail을 입력해주세요.");
    //         setGlobal('onLoad', false);
    //         return false;
    //     }

    //     if (account.password[0] === '') {
    //         alert("Password를 입력해주세요.");
    //         setGlobal('onLoad', false);
    //         return false;
    //     }

    //     axios.post("/auth/login", {
    //         'id': account.id[0],
    //         'password': account.password[0]
    //     })
    //     .then(response => {
    //         console.log(response.data);
    //         sessionStorage.setItem("access", response.data.accessToken);
    //         sessionStorage.setItem("ext", response.data.tokenExpiresIn);
    //         setGlobal('onLoad', false);
    //         alert("Login 성공"); 
    //         navigate("/home");
            
    //     })
    //     .catch(error => {
    //         console.log(error);
    //         setGlobal('onLoad', false);
    //         alert("E-Mail 또는 Password를 확인해주세요.");
    //     })
    // }

    const [loginEm, setLoginEm] = useState('');
    const [loginPw, setLoginPw] = useState('');
    const [isSignUp, setIsSignUp] = useState(false);
    
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirm, setConfirm] = useState('');
    const [name, setName] = useState('');
    const [birth, setBirth] = useState(new Date());
    const [gender, setGender] = useState('');
    const [phone, setPhone] = useState('');
    const [insta, setInsta] = useState('');

    const clickLogin = () => {
        setGlobal('onLoad', true);
        if (loginEm === '') {
            alert('Email을 입력해주세요.');
            setGlobal('onLoad', false);
            return false;
        }

        if (loginPw === '') {
            alert('Password를 입력해주세요.');
            setGlobal('onLoad', false);
            return false;
        }

        axios.post("/auth/login", {
            'id': loginEm,
            'password': loginPw
        })
        .then(response => {
            console.log(response.data);
            sessionStorage.setItem("access", response.data.accessToken);
            sessionStorage.setItem("ext", response.data.tokenExpiresIn);
            setGlobal('onLoad', false);
            alert("Login 성공"); 
            navigate("/home");
        })
        .catch(error => {
            console.log(error);
            setGlobal('onLoad', false);
            alert("E-Mail 또는 Password를 확인해주세요.");
        })
    } 

    

    return (
        <>
        <div>
            <Container style={{height:(isSignUp) ? '10vh' : '40vh', transition: 'all 0.2s linear 0s'}}>
                <FaRunning size='5em' color='white' style={{paddingTop:'40%'}}/>
                <div className='header'>Running</div>
            </Container>
            <Container className='login_container' style={{height:(isSignUp) ? '90vh' : '60vh'}}>
                <Container style={{display:(isSignUp) ? 'none' : 'block'}}>
                    <div className="header" style={{fontSize:'25px'}}>
                        <span className="login_header">LOGIN</span>
                    </div>
                    <Input label={'Email'} value={loginEm} fnChange={setLoginEm}/>
                    <Password value={loginPw} fnChange={setLoginPw}/>
                    <MuiButton text={'LOGIN'} clickEvent={clickLogin}/>
                    <MuiButton text={'SIGN UP'} clickEvent={() => setIsSignUp(true)}/>
                </Container>
                <Container style={{display:(isSignUp) ? 'block' : 'none'}}>
                    <div className="header" style={{fontSize:'25px'}}>
                        <span className="login_header">SIGN UP</span>
                    </div>
                    <Input label={'Email*'}/>
                    <Password />
                    <Password isCheck={true}/>
                    <Input label={'Name*'}/>
                    <Box sx={{display:'flex', flexDirection:'row', marginBottom:'20px'}}>
                        <Calendar label={'Birth*'} date={birth} fnChange={setBirth}/>
                        <div style={{marginLeft:'auto'}}/>
                        <SelectBox label={'Gender*'}/>
                    </Box>
                    <Input label={'Phone*'}/>
                    <Input label={'Instagram'}/>
                    <MuiButton text={'SIGN UP'} clickEvent={clickLogin}/>
                    <div style={{color:'gray'}} onClick={() => setIsSignUp(false)}>Cancle</div>
                </Container>
            </Container>
        </div>
        </>
    )
}

export default Login;