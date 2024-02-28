import {useApi} from "../../http/useApi";

export const FirstStepRegistration = (
    {onCompleteSuccessfully, onFailure}:
        { onCompleteSuccessfully: () => void, onFailure: (errorText: string) => void }
) => {

    const api = useApi();

    const register = async (e: any) => {
        e.preventDefault();
        const username = e.target.username.value;
        const email = e.target.email.value;
        const password = e.target.password.value;
        try {
            await api.fetchUnauthenticated(
                '/register/request',
                'POST',
                {
                    username: username,
                    email: email,
                    password: password
                },
                {
                    'Content-Type': 'application/json'
                }
            )
            onCompleteSuccessfully();
        } catch (e: any) {
            switch (e.status) {
                case 409:
                    onFailure('User with same username or e-mail already exists.');
                    break;
                case 400:
                    onFailure('Invalid username, password or e-mail.');
                    break;
                default:
                    onFailure(`An unexpected error occurred. (${e.status})`);
                    break;
            }
        }
    };

    return (
        <form onSubmit={register}>
            <input type="text" name="username" placeholder="Username"/>
            <input type="text" name="email" placeholder="E-mail"/>
            <input type="password" name="password" placeholder="Password"/>
            <button type="submit">Request registration</button>
        </form>
    );
};