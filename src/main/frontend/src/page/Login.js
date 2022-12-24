import { Box, Container, TextField } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useState, useContext } from "react";
import {BiUserCircle} from 'react-icons/bi';
import Header from "../components/Header";
import { Context } from "../App";
import axios from "axios";
import Password from "../components/Password";

function Login() {
    const setGlobal = useContext(Context).setGlobal;

    const borderWhite = '2px solid white';
    const navigate = useNavigate();
    const userData = {
        id: ['', 'Y', borderWhite],
        password: ['', 'Y', borderWhite],
        confirm: ['', 'Y', borderWhite],
        name: ['', 'Y', borderWhite],
        phone: ['', 'Y', borderWhite],
        birth: ['', 'Y', borderWhite],
        gender: ['gender', 'Y', borderWhite]
    };

    const [signup, setSignup] = useState(false);
    const [account, setAccount] = useState(userData);

    const onChnage = (event, type=null) => {
        if (type === null) {
            setAccount({
                ...account,
                [event.target.name]: [event.target.value, 'Y', borderWhite]
            });
        }

        if (type === 'reset') {
            setAccount(userData)
        }
    };

    const clickSignUp = () => {
        // Empty Check
        for (const item of Object.keys(account)) {
            if (account[item][0] === '') {
                alert(item + "을(를) 입력해주세요.");
                setAccount({...account, [item]:['', 'N', '2px solid red']});
                return false;
            }
        }

        // Password Check
        if (account.password[0] !== account.confirm[0]) {
            alert('비밀번호가 일치하지 않습니다.');
            setAccount({...account, ['confirm']:['', 'N', '2px solid red']});
            return false;
        }

        axios.post("/auth/signup", {
            'id': account.id[0],
            'password': account.password[0],
            'name': account.name[0],
            'phone': account.phone[0],
            'gender': account.gender[0],
            'birth': account.birth[0]
        })
        .then(response => {
            console.log(response);
            if (response.data.result === "success") {
                alert('회원가입이 완료되었습니다.');
                onChnage('', 'reset');
                setSignup(false);
            } else throw new Error("회원가입 실패.");
        })
        .catch(error => {
            console.log(error)
            alert('서버에서 오류가 발생했습니다.');
        })
    };

    // Login
    const clickLogin = () => {
        setGlobal('onLoad', true);
        if (account.id[0] === '') {
            alert("E-mail을 입력해주세요.");
            setGlobal('onLoad', false);
            return false;
        }

        if (account.password[0] === '') {
            alert("Password를 입력해주세요.");
            setGlobal('onLoad', false);
            return false;
        }

        axios.post("/auth/login", {
            'id': account.id[0],
            'password': account.password[0]
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
        <Header />  
        <div>
            <div style={{paddingTop:'100px'}}></div>
            <BiUserCircle size='5em' color='white'/>
            <div className='header'>LOG IN</div>
            <Container>
                <Box sx={{background:'white', borderRadius:'20px', marginBottom: '10px'}}>
                    <TextField size='small' sx={{'& fieldset':{borderRadius: '20px'}, width:'100%'}} label='Email' />
                </Box>
                <Box sx={{background:'white', borderRadius:'20px', marginBottom: '10px'}}>
                    <Password />
                </Box>
            </Container>
            {/* <div><input className='input_type' type={"text"} value={account.id[0]} name={"id"} placeholder={"e-mail"} onChange={onChnage} style={{border:account.id[2]}}></input></div>
            <div><input className='input_type' type={"password"} value={account.password[0]} name={"password"} placeholder={"password"} onChange={onChnage} style={{border:account.password[2]}}></input></div> */}
            <div className="sign-in-wrap" style={{visibility:(signup === false ? 'visible' : 'hidden'), opacity:(signup === false ? 100 : 0)}}>
                <div>
                    <input className="button_type" type={"button"} value={"확인"} onClick={() => clickLogin()}></input>
                    <input className="button_type" type={"button"} value={"회원가입"} onClick={() => setSignup(true)}></input>
                </div>
            </div>
            <div className='sign-up-wrap' style={{visibility:(signup === true ? 'visible' : 'hidden'), opacity:(signup === false ? 0 : 100)}}>
                <input className='input_type' type={"password"} value={account.confirm[0]} name={"confirm"} placeholder={"password confirm"} onChange={onChnage} style={{border:account.confirm[2]}}></input>
                <input className='input_type' type={"text"} value={account.name[0]} name={"name"} placeholder={"name"} onChange={onChnage} style={{border:account.name[2]}}></input>
                <input className='input_type' type={"text"} value={account.phone[0]} name={"phone"} placeholder={"phone"} onChange={onChnage} style={{border:account.phone[2]}}></input>
                <input className='input_type' type={"text"} value={account.birth[0]} name={"birth"} placeholder={"birth"} onChange={onChnage} style={{border:account.birth[2]}}></input>
                <div>
                        <select className='select-type' value={account.gender[0]} placeholder={"gender"} name={"gender"} onChange={onChnage} style={{border:account.gender[2]}}>
                            <option defaultValue={"gender"} hidden>Gender</option>
                            <option value={"M"}>Male</option>
                            <option value={"F"}>Femail</option>
                        </select>
                </div>  
                <input className="button_type" type={"button"} value={"계속"} onClick={() => clickSignUp()}></input>
                <input className="button_type" type={"button"} value={"취소"} onClick={() => {setSignup(false); onChnage('', 'reset')}}></input>
            </div>
        </div>
        </>
    )
}

export default Login;