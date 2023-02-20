/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import BasketComponent from '@/entities/basket/basket.vue';
import BasketClass from '@/entities/basket/basket.component';
import BasketService from '@/entities/basket/basket.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Basket Management Component', () => {
    let wrapper: Wrapper<BasketClass>;
    let comp: BasketClass;
    let basketServiceStub: SinonStubbedInstance<BasketService>;

    beforeEach(() => {
      basketServiceStub = sinon.createStubInstance<BasketService>(BasketService);
      basketServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<BasketClass>(BasketComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          basketService: () => basketServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      basketServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllBaskets();
      await comp.$nextTick();

      // THEN
      expect(basketServiceStub.retrieve.called).toBeTruthy();
      expect(comp.baskets[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      basketServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(basketServiceStub.retrieve.callCount).toEqual(1);

      comp.removeBasket();
      await comp.$nextTick();

      // THEN
      expect(basketServiceStub.delete.called).toBeTruthy();
      expect(basketServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
