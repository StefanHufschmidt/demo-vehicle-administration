import {useState} from "react";
import {FirstStepRegistration} from "./register-components/first-step-registration";

export function Register() {
    const [errorText, setErrorText] = useState<string>()
    const [concludedFirstStep, setConcludedFirstStep] = useState<boolean>()

    return (
        <div>
            <h1>Register new account</h1>
            <p>{errorText}</p>
            {concludedFirstStep ? <p>First step done</p>
                : <FirstStepRegistration
                    onCompleteSuccessfully={() => setConcludedFirstStep(true)}
                    onFailure={(error) => setErrorText(error)}
                />}
        </div>
    );
}