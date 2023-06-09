import { Component, OnInit } from '@angular/core';
import { ActivitiesService, Activity } from '../service/activities.service';
import { Flight, FlightsService } from '../service/flights.service';
import { Hotel, HotelService } from '../service/hotel.service';
import { Travel, TravelpackageService } from '../service/travelpackage.service';

@Component({
  selector: 'app-travelagent',
  templateUrl: './travelagent.component.html',
  styleUrls: ['./travelagent.component.css']
})
export class TravelagentComponent implements OnInit {
  travels: Travel[] = [];
  flightOptions: Flight[] = [];
  hotelOptions: Hotel[] = [];
  activitiesOptions: Activity[] = [];
  showAddForm: boolean = false;
  searchKeyword: string = '';
  filteredTravels: Travel[] = [];
  showAddPopup: boolean = false; 

  constructor(
    private activitiesservice: ActivitiesService,
    private flightservice: FlightsService,
    private hotelservice: HotelService,
    private travelService: TravelpackageService
  ) {}

  ngOnInit() {
    this.getTravelList();
    this.getFlightOptions();
    this.getHotelOptions();
    this.getActivitiesOptions();
  }

  newTravel: Travel = new Travel();


  filterTravels() {
    this.filteredTravels = this.travels.filter((travel) =>
    travel?.travelPackageName?.toLowerCase().includes(this.searchKeyword.toLowerCase())

    );
  }

  addNewTravel() {
    this.showAddForm = true;
    this.showAddPopup = true;

  }

  deleteNewTravel() {
    this.showAddForm = false;
  }

  saveTravel(travel: Travel) {
    travel.activitiesId = Number(travel.activitiesId);
    travel.flightId = Number(travel.flightId);
    travel.hotelId = Number(travel.hotelId);
    travel.totalPrice = Number(travel.totalPrice);
    travel.noOfDays = Number(travel.noOfDays);
    this.cancel();
    this.travelService.createTravel(travel).subscribe(
      (response) => {
        console.log('Travel saved successfully');
        this.resetForm();
        this.showAddForm = false;
        this.getTravelList();
      },
      (error) => {
        console.log('Error occurred while saving travel:', error);
      }
    );
  }

  getTravelList() {
    this.travelService.getTravelList().subscribe(
      (travels: Travel[]) => {
        console.log(travels);
        this.travels = travels.map((travel) => Object.assign({}, travel, { editable: false }));
        this.filteredTravels = this.travels.map((travel) => Object.assign({}, travel));
        this.filterTravels(); // Apply initial filtering
      },
      (error) => {
        console.log('Error occurred while retrieving travel list:', error);
      }
    );
  }


  getHotelNameById(hotelId: number): string {
    const hotel = this.hotelOptions.find((hotel) => hotel.hotelId === hotelId);
    return hotel ? hotel.hotelName || '' : '';
  }

  getFlightNameById(flightId: number): string {
    const flight = this.flightOptions.find((flight) => flight.flightId === flightId);
    return flight ? flight.airline || '' : '';
  }

  getActivityNameById(activityId: number): string {
    const activity = this.activitiesOptions.find((activity) => activity.activitiesId === activityId);
    return activity ? activity.activityName || '' : '';
  }

  cancel() {
      this.showAddPopup = false;
  }

  toggleEdit(index: number) {
    if (this.travels[index].editable) {
      this.updateTravel(index);
    }
    this.travels[index].editable = !this.travels[index].editable;
  }

  updateTravel(index: number) {
    const travel = this.travels[index];
    this.travelService.updateTravel(travel).subscribe(
      (response) => {
        console.log('Travel updated successfully');
        travel.editable = false;
      },
      (error) => {
        console.log('Error occurred while updating travel:', error);
      }
    );
  }

  deleteTravel(index: number) {
    const travelId = this.travels[index].travelPackageId;
    this.travelService.deleteTravel(Number(travelId)).subscribe(
      (response) => {
        console.log('Travel deleted successfully');
        this.getTravelList();
      },
      (error) => {
        console.log('Error occurred while deleting travel:', error);
      }
    );
  }

  resetForm() {
    this.newTravel = new Travel();
  }

  getFlightOptions() {
    this.flightservice.getFlights().subscribe(
      (options: Flight[]) => {
        for (let i = 0; i < options.length; i++) {
          this.flightOptions.push(options[i]);
        }
      },
      (error) => {
        console.log('Error occurred while retrieving flight options:', error);
      }
    );
  }

  getHotelOptions() {
    this.hotelservice.getHotelList().subscribe(
      (options: Hotel[]) => {
        for (let i = 0; i < options.length; i++) {
          this.hotelOptions.push(options[i]);
        }

      },
      (error) => {
        console.log('Error occurred while retrieving hotel options:', error);
      }
    );
  }

  getActivitiesOptions() {
    this.activitiesservice.getActivities().subscribe(
      (options: Activity[]) => {
        for (let i = 0; i < options.length; i++) {
          this.activitiesOptions.push(options[i]);
        }
      },
      (error) => {
        console.log('Error occurred while retrieving activities options:', error);
      }
    );
  }
}
