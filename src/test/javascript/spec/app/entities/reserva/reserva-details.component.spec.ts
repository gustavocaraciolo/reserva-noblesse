/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ReservaDetailComponent from '@/entities/reserva/reserva-details.vue';
import ReservaClass from '@/entities/reserva/reserva-details.component';
import ReservaService from '@/entities/reserva/reserva.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Reserva Management Detail Component', () => {
    let wrapper: Wrapper<ReservaClass>;
    let comp: ReservaClass;
    let reservaServiceStub: SinonStubbedInstance<ReservaService>;

    beforeEach(() => {
      reservaServiceStub = sinon.createStubInstance<ReservaService>(ReservaService);

      wrapper = shallowMount<ReservaClass>(ReservaDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { reservaService: () => reservaServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundReserva = { id: 123 };
        reservaServiceStub.find.resolves(foundReserva);

        // WHEN
        comp.retrieveReserva(123);
        await comp.$nextTick();

        // THEN
        expect(comp.reserva).toBe(foundReserva);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundReserva = { id: 123 };
        reservaServiceStub.find.resolves(foundReserva);

        // WHEN
        comp.beforeRouteEnter({ params: { reservaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.reserva).toBe(foundReserva);
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
