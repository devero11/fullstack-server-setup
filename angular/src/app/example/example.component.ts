import { Component } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http'; //
import { inject } from '@angular/core';
import { OnInit} from '@angular/core'
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-example',
  imports: [FormsModule, CommonModule],
  templateUrl: './example.component.html',
  styleUrl: './example.component.css'
})
export class ExampleComponent implements OnInit {

  username:string = ""
  email:string = ""
  password:string = ""

  fetchedUsername:string|null = null

  token:string|null = null

  http = inject(HttpClient)
  private apiUrl = 'http://localhost:8080'; // your Spring Boot backend

  printForm():void{
    console.log(this.fetchedUsername)
  }
  ngOnInit(): void {
      this.token = localStorage.getItem("token")
      if(this.token)
        this.getUsernameF()

      console.log(this.fetchedUsername)
   }


  register(username: string, email: string, password: string): Observable<any> {
    const body = { username, email, password };
    return this.http.post(`${this.apiUrl}/auth/register`, body);
  }

  // 🟢 LOGIN USER (JSON body, returns token)
  login(username: string, password: string): Observable<any> {
    const body = { username, password };
    return this.http.post<{ token: string }>(`${this.apiUrl}/auth/login`, body).pipe(
      tap((response: {token:string})=> {
        localStorage.setItem('token', response.token);
      })
    );
  }

  getUsername(): Observable<string> {

    if(!this.token)
      throw new Error('User is not logged in');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`
    });

    return this.http.get(`${this.apiUrl}/user/username`, {
      headers,
      responseType: 'text' as const   // 👈 THIS tells Angular it’s plain text
    });
  }

  registerF() {
    this.register(this.username, this.email, this.password).subscribe({
      next: res => this.saveToken(res.token),
      error: err => console.error('Register error:', err)
    });
  }

  loginF() {
    this.login(this.username, this.password).subscribe({
      next: res => this.saveToken(res.token),

      error: err => console.error('Login error:', err)
    });

  }

  getUsernameF() {

    this.getUsername().subscribe({
      next: res => this.fetchedUsername = res,
      error: err => console.error('Get username error:', err)
    });
  }

  saveToken(token:string){
    this.token=token
    localStorage.setItem("token", token)
    this.getUsernameF()
    console.log(localStorage.getItem("token"))
  }
  logout(){
    this.fetchedUsername= null
    this.token = null
    localStorage.removeItem("token")
  }
}
