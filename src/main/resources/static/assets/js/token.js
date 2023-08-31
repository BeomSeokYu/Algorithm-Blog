const token = searchParam('token');

if (token) {
    localStorage.setItem("access_token", token);
}

let navLoginUl = document.getElementById('nav-login-ul');
let registerBtnDiv = document.getElementById('register-btn-div');
let modDelDiv = document.getElementById('mod-del-div');
if (localStorage.getItem("access_token") == null) {
    navLoginUl.innerHTML = '<li class="nav-item"><a href="/login" class="nav-link link-body-emphasis px-2">Login</a></li>';
    if (registerBtnDiv != null) {
        registerBtnDiv.innerHTML = '';
    }
    if (modDelDiv != null) {
        modDelDiv.innerHTML = '';
    }
} else {
    navLoginUl.innerHTML = '<li class="nav-item"><button class="nav-link link-body-emphasis px-2" onclick="logout()">Logout</button></li>';
}

function searchParam(key) {
    return new URLSearchParams(location.search).get(key);
}

function logout() {
    localStorage.removeItem('access_token');
    location.replace('/logout')
}