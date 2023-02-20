/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import BasketUpdateComponent from '@/entities/basket/basket-update.vue';
import BasketClass from '@/entities/basket/basket-update.component';
import BasketService from '@/entities/basket/basket.service';

import OrderService from '@/entities/order/order.service';

import ItemService from '@/entities/item/item.service';

import CustomerService from '@/entities/customer/customer.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Basket Management Update Component', () => {
    let wrapper: Wrapper<BasketClass>;
    let comp: BasketClass;
    let basketServiceStub: SinonStubbedInstance<BasketService>;

    beforeEach(() => {
      basketServiceStub = sinon.createStubInstance<BasketService>(BasketService);

      wrapper = shallowMount<BasketClass>(BasketUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          basketService: () => basketServiceStub,
          alertService: () => new AlertService(),

          orderService: () =>
            sinon.createStubInstance<OrderService>(OrderService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          itemService: () =>
            sinon.createStubInstance<ItemService>(ItemService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          customerService: () =>
            sinon.createStubInstance<CustomerService>(CustomerService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.basket = entity;
        basketServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(basketServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.basket = entity;
        basketServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(basketServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBasket = { id: 123 };
        basketServiceStub.find.resolves(foundBasket);
        basketServiceStub.retrieve.resolves([foundBasket]);

        // WHEN
        comp.beforeRouteEnter({ params: { basketId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.basket).toBe(foundBasket);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
