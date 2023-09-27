import { animate, style, transition, trigger } from '@angular/animations';
import { Component, Input, OnInit, ViewEncapsulation } from '@angular/core';

export interface HalfCloseSideNavConfig {
  preSelectIndex: number;
  isAlwaysExpanded: boolean;
  isOpened: boolean;
  autoExpand: boolean;
  currentUserRoles: string[];
  items: HalfCloseSideNavItem[];
}

export interface HalfCloseSideNavItem {
  icon: string;
  label: string;
  routerLink: string;
  userRolesAccess: string[];
}

@Component({
  selector: 'app-half-close-side-nav-component',
  templateUrl: './half-close-side-nav.component.html',
  styleUrls: ['./half-close-side-nav.component.scss'],
  encapsulation: ViewEncapsulation.None,
  animations: [
    trigger(
      'inOutAnimation',
      [
        transition(
          ':enter',
          [
            style({ width: 0, opacity: 0 }),
            animate('0.1s ease-out',
                    style({ width: '10em', opacity: 0 }))
          ]
        ),
        transition(
          ':leave',
          [
            style({ width: '10em', opacity: 0 }),
            animate('0.1s ease-in',
                    style({ width: 0, opacity: 0 }))
          ]
        )
      ]
    )
  ]
})
export class HalfCloseSideNavComponent implements OnInit {

  isAlwaysExpanded = false;
  isOpened = false;

  selectedIndex = -1;
  autoExpand = false;

  items: HalfCloseSideNavItem[] = [];

  private _config: HalfCloseSideNavConfig;

  @Input()
  set config(config: HalfCloseSideNavConfig) {
    this._config = config;
  }
  get config(): HalfCloseSideNavConfig {
    return this._config;
  }

  constructor() {
    // default config
    this.config = {
      preSelectIndex: -1,
      isAlwaysExpanded: false,
      isOpened: false,
      autoExpand: false,
      currentUserRoles: [],
      items: []
    };
  }

  ngOnInit(): void {
    // init
    this._initialize(this.config);
  }

  /**
   * filters the given items list against the "currentUserRoles" using the item internal "userRolesAccess" list.
   * @param currentUserRoles the given roles uf the current user
   * @param items the list of all navigation items
   */
  filterItemsAccessByUserRoles(currentUserRoles: string[], items: HalfCloseSideNavItem[]): HalfCloseSideNavItem[] {
    const result = [];
    items.forEach(item => {
      let accessGranted = false;
      for (let i = 0; i < item.userRolesAccess.length; i++) {
        if (currentUserRoles.includes(item.userRolesAccess[i]) ||
        // or wild card "*" to allow this item always
        (item.userRolesAccess[i] === '*')) {
          accessGranted = true;
          break;
        }
      }
      if (accessGranted) {
        result.push(item);
      }
    });
    return result;
  }

  /**
   * Initializes the component by the given config data structure
   */
  private _initialize(config: HalfCloseSideNavConfig) {
    this.selectedIndex = config.preSelectIndex;
    this.isAlwaysExpanded = config.isAlwaysExpanded;
    this.isOpened = config.isOpened;
    this.autoExpand = config.autoExpand;
    this.items = this.filterItemsAccessByUserRoles(config.currentUserRoles, config.items);
  }

  /**
   * Updates the selection index of the item
   * @param event the triggered event ref
   * @param itemIndex the item index
   */
  onItemSelected(itemIndex: number) {
    this.selectedIndex = itemIndex;
  }

  mouseEnter() {
    if (!this.isAlwaysExpanded && this.autoExpand) {
      this.isOpened = true;
    }
  }

  mouseLeave() {
    if (!this.isAlwaysExpanded && this.autoExpand) {
      this.isOpened = false;
    }
  }

  /**
   * toggles the half-close-nav menu.
   * half-open state (only icons visible) <=> fully open (icons and labels visible)
   */
  toggle() {
    if (!this.isAlwaysExpanded) {
      this.isOpened = !this.isOpened;
    }
  }

  /**
   * This empty method call forces the component to redraw
   * between target states while animation is running
   * @param event the animation event ref
   */
  onAnimationEvent() {}
}
