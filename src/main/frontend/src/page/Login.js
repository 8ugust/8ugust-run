import { Container } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useState, useContext } from "react";
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
        if (email === '') {validation('Email'); return false;}
        if (password === '') {validation('Password'); return false;}

        if (!email.match(/[\w\-\.]+\@[\w\-\.]/)) {  // eslint-disable-line
            alert('잘못된 Eamil 형식입니다.');
            setGlobal('onLoad', false);
            return false;
        }

        axios.post("/auth/login", {
            'id': email,
            'password': password
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

    const clickSignUp = () => {
        // Empty Check
        if (email === '') {validation('Email'); return false;}
        if (password === '') {validation('password'); return false;}
        if (confirm === '') {validation('confirm'); return false;}
        if (name === '') {validation('name'); return false;}
        if (birth === '') {validation('birth'); return false;}
        if (gender === '') {validation('gender'); return false;}
        if (phone === '') {validation('phone'); return false;}

        // Email Check
        if (!email.match(/[\w\-\.]+\@[\w\-\.]/)) {  // eslint-disable-line
            alert('잘못된 Eamil 형식입니다.');
            setGlobal('onLoad', false);
            return false;
        }

        // Phone Check
        if (phone.length !== 13) {
            alert('잘못된 Phone 번호입니다.');
            setGlobal('onLoad', false);
            return false;
        }

        axios.post("/auth/signup", {
            'id': email,
            'password': password,
            'name': name,
            'phone': phone,
            'gender': gender,
            'birth': birth,
            'instagram': insta
        })
        .then(response => {
            console.log(response);
            if (response.data.result === "success") {
                alert('회원가입이 완료되었습니다.');
                setGlobal('onLoad', false);
                setIsSignUp(false); 
            } else throw new Error("회원가입 실패.");
        })
        .catch(error => {
            console.log(error)
            alert('서버에서 오류가 발생했습니다.');
        })

        
    }

    const validation = (msg) => {
        alert(msg + ' 을(를) 입력해주세요.');
        setGlobal('onLoad', false);
    }

    const fnChangeSignType = () => {
        // Clear Input
        setEmail(''); setPassword('');
        setName(''); setBirth(new Date());
        setGender(''); setPhone(''); setInsta('');

        // Change Type
        setIsSignUp(!isSignUp);
    }
    


    return (
        <>
        <div>
            <Container style={{height:(isSignUp) ? '10vh' : '40vh', transition: 'all 0.2s linear 0s'}}>
                <FaRunning size='5em' color='white' style={{paddingTop:'40%'}}/>
                <div className='header'>Running</div>
            </Container>
            <Container className='login_container' style={{height:(isSignUp) ? '90vh' : '60vh'}}>
                <Container>
                    <div className="header" style={{fontSize:'25px'}}>
                        <span className="login_header">{isSignUp ? 'SIGN UP' : 'LOGIN'}</span>
                    </div>
                    <Input label={'Email'} value={email} fnChange={setEmail} req={isSignUp ? true : false}/>
                    <Password value={password} fnChange={setPassword} req={isSignUp ? true : false}/>
                </Container>
                <Container style={{display:(isSignUp) ? 'none' : 'block'}}>
                    <MuiButton text={'LOGIN'} clickEvent={clickLogin}/>
                    <MuiButton text={'SIGN UP'} clickEvent={() => fnChangeSignType()}/>
                </Container>
                <Container style={{display:(isSignUp) ? 'block' : 'none'}}>
                    <Password isCheck={true} value={confirm} fnChange={setConfirm} req={true}/>
                    <Input label={'Name'} value={name} fnChange={setName} req={true}/>
                    <Box sx={{display:'flex', flexDirection:'row', marginBottom:'20px'}}>
                        <Calendar label={'Birth'} date={birth} fnChange={setBirth} req={true}/>
                        <div style={{marginLeft:'auto'}}/>
                        <SelectBox label={'Gender'} value={gender} fnChange={setGender} req={true}/>
                    </Box>
                    <Input label={'Phone'} value={phone} fnChange={setPhone} req={true}/>
                    <Input label={'Instagram'} value={insta} fnChange={setInsta}/>
                    <MuiButton text={'SIGN UP'} clickEvent={clickSignUp}/>
                    <div className="cancle" onClick={() => fnChangeSignType()}>Cancle</div>
                </Container>
            </Container>
        </div>
        </>
    )
}

export default Login;