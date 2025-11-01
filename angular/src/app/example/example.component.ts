import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http'; //
import { inject } from '@angular/core';
import { OnInit} from '@angular/core'
import { observableToBeFn } from 'rxjs/internal/testing/TestScheduler';
@Component({
  selector: 'app-example',
  imports: [],
  templateUrl: './example.component.html',
  styleUrl: './example.component.css'
})
export class ExampleComponent implements OnInit {

  http = inject(HttpClient)
sendMessage() {
    const url = 'http://localhost:11434/api/chat';
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    const body = {
      model: 'therapist',
      stream: false,
      messages: [
        {
          role: 'system',
          content:
            'You are a compassionate, nonjudgmental therapist AI. Speak calmly, ask reflective questions, and encourage emotional insight without giving medical advice.'
        },
        {
          role: 'user',
          content:
            'Lately I’ve been feeling anxious about work and can’t seem to relax. What should I do?'
        }
      ]
    };

    this.http.post(url, body, { headers }).subscribe({
      next: (response) => console.log('AI Response:', response),
      error: (err) => console.error('Error:', err)
    });
  }
  ngOnInit(): void {
     this.sendMessage()
  }
}
