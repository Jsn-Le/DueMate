import '../pages/Register.css'

function Register() {
    return (
        <main>
            <div className="login-container">
                <div className="branding">
                    <h1>D U E M A T E</h1>
                    <h4>Create your account</h4>
                </div>

                <div className="login-form">
                    <input type="email" placeholder="Email" />
                    <input type="password" placeholder="Password" />
                    <input type="password" placeholder="Confirm Password" />
                    <button>Register</button>
                </div>

                <div className="account-links">
                    <h5>Already have an account? <span className="login">Login</span></h5>
                </div>
            </div>
        </main>
    )
}

export default Register