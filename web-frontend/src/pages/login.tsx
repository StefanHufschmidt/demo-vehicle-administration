import {useState} from "react";
import {useUser} from "../auth/UserContext";
import {useApi} from "../http/useApi";

export function Login() {
    const user = useUser();
    const api = useApi();
    const [errorText, setErrorText] = useState<string>()

    const login = async (e: any) => {
        e.preventDefault();
        const username = e.target.username.value;
        const password = e.target.password.value;
        try {
            setErrorText('');
            await api.fetchUnauthenticated<void>(
                '/login',
                'POST',
                {
                    username: username,
                    password: password
                },
                {
                    'Content-Type': 'application/json'
                }
            );
            user.login(username, password);
        } catch (e) {
            console.error(e);
            setErrorText('Login failed.');
        }
    };

    return (
        <div className="Login-layout">
            <p className="Login-error">{errorText}</p>
            <form className="Login-form" onSubmit={login}>
                <input type="text" name="username" placeholder="username" />
                <input type="password" name="password" placeholder="password" />
                <button type="submit">Login</button>
            </form>
            <hr />
            <a href="/register">Register</a>
        </div>
    );
}