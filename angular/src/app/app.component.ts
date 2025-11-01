import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ExampleComponent} from './example/example.component'


import { HttpClientModule } from '@angular/common/http'; //
@Component({
  selector: 'app-root',
  imports: [HttpClientModule,RouterOutlet, ExampleComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'angular';


}
